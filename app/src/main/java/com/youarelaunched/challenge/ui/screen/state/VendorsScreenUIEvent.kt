package com.youarelaunched.challenge.ui.screen.state

import com.youarelaunched.challenge.base.UiEvent
import com.youarelaunched.challenge.data.repository.model.Vendor

sealed class VendorsScreenUIEvent : UiEvent{

    data class ShowList(val vendorsList: List<Vendor>): VendorsScreenUIEvent()
}
