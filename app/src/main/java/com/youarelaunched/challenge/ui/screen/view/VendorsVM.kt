package com.youarelaunched.challenge.ui.screen.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.ui.screen.state.const.VendorsScreenConst.AUTO_SEARCH_DEBOUNCE_MS
import com.youarelaunched.challenge.ui.screen.state.const.VendorsScreenConst.AUTO_SEARCH_MIN_WORD_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "VendorsVM"

@HiltViewModel
class VendorsVM @Inject constructor(
    private val repository: VendorsRepository

) : ViewModel() {

    private val _uiState = MutableStateFlow(
        VendorsScreenUiState(
            vendors = null,
            searchQuery = ""
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        getVendors()
        initAutoSearchFlow()
    }

    private fun initAutoSearchFlow(){
        viewModelScope.launch {
            uiState
                .map { it.searchQuery }
                .filter { it.length >= AUTO_SEARCH_MIN_WORD_LENGTH }
                .debounce(AUTO_SEARCH_DEBOUNCE_MS)
                .collect {
                    Log.d(TAG, "auto search")
                    onSearch(it)
                }
        }
    }

    fun onSearch(searchQuery: String) {
        getVendors(searchQuery)
    }

    fun onSearchQueryUpdate(searchQuery: String) {
        _uiState.update {
            it.copy(
                searchQuery = searchQuery
            )
        }
    }

    fun getVendors(searchQuery: String = "") {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    vendors = repository.getVendors(searchQuery)
                )
            }
        }
    }
}