package com.youarelaunched.challenge

import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.data.repository.VendorsRepositoryImpl
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.screen.view.VendorsVM
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class VendorVMTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeVendorList = createFakeVendorsList()

    @Test
    fun `getVendors return not empty list`() = runTest {
        // Given: a VendorsRepository mocked instance
        val vendorsRepository = mockk<VendorsRepositoryImpl>()
        coEvery { vendorsRepository.getVendors(any()) } returns fakeVendorList

        // And: VendorsVM instance
        val vendorsViewModel = VendorsVM(vendorsRepository)

        // When: getVendors is called
        vendorsViewModel.onSearchForVendors("")
        advanceUntilIdle()

        // Then: the Vendors list in not empty and not null
        val result = vendorsViewModel.state.value.vendors
        assert(result.isNotEmpty())

    }

    @Test
    fun `getVendors return null if error happens`() = runTest {
        // Given: a VendorsRepository mocked instance
        val vendorsRepository = mockk<VendorsRepository>()
        coEvery { vendorsRepository.getVendors(any()) } throws IllegalStateException()

        // And: VendorsVM instance
        val vendorsViewModel = VendorsVM(vendorsRepository)

        // When: getVendors is called
        vendorsViewModel.onSearchForVendors("")
        advanceUntilIdle()

        // Then: the Vendors list in empty or null
        val result = vendorsViewModel.state.value.vendors
        assert(result.isEmpty())
    }

    private fun createFakeVendorsList(): List<Vendor>{
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