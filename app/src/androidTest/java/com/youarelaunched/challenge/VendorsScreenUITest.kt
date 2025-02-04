package com.youarelaunched.challenge

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.youarelaunched.challenge.middle.R
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUIState
import com.youarelaunched.challenge.ui.screen.view.VendorsScreen
import com.youarelaunched.challenge.ui.theme.VendorAppTheme
import org.junit.Rule
import org.junit.Test

class VendorsScreenUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeVendorList = createFakeVendorsList()


    @Test
    fun When_vendors_list_is_empty_NoResult_screen_must_be_visible() {
        // Given: a VendorsScreenUiState instance
        val uiState = VendorsScreenUIState(vendors = emptyList(), searchQuery = "")
        composeTestRule.setContent {
            VendorAppTheme {
                VendorsScreen(
                    uiState = uiState,
                    onSearch = {},
                )
            }
        }

        // With: empty list title
        val emptyListTitle = composeTestRule.activity.getString(R.string.empty_list_title)

        //And: empty list Description
        val emptyListDescription = composeTestRule.activity.getString(R.string.empty_list_description)

        //Then: assert that empty list title is displayed
        composeTestRule.onNodeWithText(emptyListTitle).assertIsDisplayed()

        //And: empty list description is displayed
        composeTestRule.onNodeWithText(emptyListDescription).assertIsDisplayed()
    }

    @Suppress("IllegalIdentifier")
    @Test
    fun When_vendors_list_is_not_empty_at_least_one_item_is_visible() {
        // Given: a VendorsScreenUiState instance
        val uiState = VendorsScreenUIState(vendors = fakeVendorList, searchQuery = "")
        composeTestRule.setContent {
            VendorAppTheme {
                VendorsScreen(
                    uiState = uiState,
                    onSearch = {},
                )
            }
        }

        // With: vendors item description
        val vendorItemContentDescription = composeTestRule.activity.getString(R.string.cd_vendor_item)

        //That: assert that at least 1 vendor item is displayed
        composeTestRule.onAllNodesWithContentDescription(vendorItemContentDescription)
            .apply {
                assertCountEquals(fakeVendorList.size)
                this[0].assertIsDisplayed()
            }
    }

    private fun createFakeVendorsList(): List<Vendor> {
        val fakeVendor = Vendor(
            id = 0,
            companyName = "testCompanyName",
            coverPhoto = "https://test.png",
            area = "testArea",
            favorite = false,
            categories = null,
            tags = null
        )
        return listOf(fakeVendor, fakeVendor)
    }
}
