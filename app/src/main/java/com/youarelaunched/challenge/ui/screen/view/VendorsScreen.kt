package com.youarelaunched.challenge.ui.screen.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.ui.screen.state.const.VendorsScreenConst
import com.youarelaunched.challenge.ui.screen.view.components.*
import com.youarelaunched.challenge.ui.theme.VendorAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VendorsRoute(
    viewModel: VendorsVM
) {
    val uiState by viewModel.uiState.collectAsState()

    VendorsScreen(uiState = uiState, onSearch = {
        viewModel.onSearch(it)
    }, onSearchQueryUpdate = {
        viewModel.onSearchQueryUpdate(it)
    })
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VendorsScreen(
    uiState: VendorsScreenUiState,
    onSearch: (String) -> Unit,
    onSearchQueryUpdate: (String) -> Unit
) {

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
                onSearchQueryUpdate(query)
            })

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            if (uiState.vendors.isNullOrEmpty()) {
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