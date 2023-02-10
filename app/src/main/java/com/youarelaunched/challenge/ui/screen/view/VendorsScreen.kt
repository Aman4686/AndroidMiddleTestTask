package com.youarelaunched.challenge.ui.screen.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUIState
import com.youarelaunched.challenge.ui.screen.state.const.VendorsScreenConst.AUTO_SEARCH_DEBOUNCE_MS
import com.youarelaunched.challenge.ui.screen.state.const.VendorsScreenConst.AUTO_SEARCH_MIN_WORD_LENGTH
import com.youarelaunched.challenge.ui.screen.view.components.*
import com.youarelaunched.challenge.ui.theme.VendorAppTheme
import com.youarelaunched.challenge.utils.debounce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VendorsRoute(
    viewModel: VendorsVM
) {
    val uiState by viewModel.state.collectAsState()

    VendorsScreen(uiState = uiState,
        onSearch = {
        viewModel.onSearchForVendors(it)
    })
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VendorsScreen(
    uiState: VendorsScreenUIState,
    onSearch: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val autoSearchWithDebounce = debounce(scope = scope, action = onSearch)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = VendorAppTheme.colors.background,
        snackbarHost = { ChatsumerSnackbar(it) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 24.dp, 16.dp, 0.dp)
        ) {
            SearchBar(onSearchClick = onSearch, onSearchQueryChanged = { query ->

                if(query.length >= AUTO_SEARCH_MIN_WORD_LENGTH) {
                    autoSearchWithDebounce.invoke(query)
                }
            })

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            if (uiState.vendors.isEmpty()) {
                NoResult()
            } else {
                VendorsList(vendorsList = uiState.vendors)
            }
        }
    }
}

@Composable
private fun VendorsList(vendorsList: List<Vendor>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(vendorsList) { vendor ->
            VendorItem(
                vendor = vendor
            )
        }
    }
}