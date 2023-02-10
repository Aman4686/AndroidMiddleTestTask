package com.youarelaunched.challenge.ui.screen.view

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.youarelaunched.challenge.base.BaseViewModel
import com.youarelaunched.challenge.base.Reducer
import com.youarelaunched.challenge.base.TimeCapsule
import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.screen.state.*
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
) : BaseViewModel<VendorsScreenUIState, VendorsScreenUIEvent>() {

    private val reducer = VendorsReducer(VendorsScreenUIState.initial())

    override val state: StateFlow<VendorsScreenUIState>
        get() = reducer.state

    private fun sendEvent(event: VendorsScreenUIEvent) {
        reducer.sendEvent(event)
    }

    init {
        viewModelScope.launch {
            val allVendorsItemQuery = ""

            val vendors = getVendors(allVendorsItemQuery)
            sendEvent(VendorsScreenUIEvent.ShowList(vendors))
        }
    }

    fun onSearchForVendors(searchQuery: String) {
        viewModelScope.launch {
            val vendors = getVendors(searchQuery)
            sendEvent(VendorsScreenUIEvent.ShowList(vendors))
        }
    }

    private suspend fun getVendors(searchQuery: String): List<Vendor> {
        return repository.getVendors(searchQuery)
    }

    private class VendorsReducer(initial: VendorsScreenUIState) :
        Reducer<VendorsScreenUIState, VendorsScreenUIEvent>(initial) {
        override fun reduce(oldState: VendorsScreenUIState, event: VendorsScreenUIEvent) {
            when (event) {
                is VendorsScreenUIEvent.ShowList -> {
                    setState(oldState.copy(vendors = event.vendorsList))
                }
            }
        }
    }
}