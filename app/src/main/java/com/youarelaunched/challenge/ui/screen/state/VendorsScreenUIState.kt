package com.youarelaunched.challenge.ui.screen.state

import com.youarelaunched.challenge.base.UiState
import com.youarelaunched.challenge.data.repository.model.Vendor

data class VendorsScreenUIState(
    val vendors: List<Vendor>,
    val searchQuery: String
) : UiState {
    companion object {
        fun initial() = VendorsScreenUIState(
            vendors = emptyList(),
            searchQuery = ""
        )
    }
}