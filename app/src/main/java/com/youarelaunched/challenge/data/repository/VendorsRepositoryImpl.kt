package com.youarelaunched.challenge.data.repository

import com.youarelaunched.challenge.data.network.api.ApiVendors
import com.youarelaunched.challenge.data.network.models.NetworkCategory
import com.youarelaunched.challenge.data.network.models.NetworkVendor
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.data.repository.model.VendorCategory
import com.youarelaunched.challenge.di.DispatcherIo
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.text.Typography.bullet

class VendorsRepositoryImpl @Inject constructor(
    @DispatcherIo val workDispatcher: CoroutineDispatcher,
    private val api: ApiVendors
) : VendorsRepository {

    override suspend fun getVendors(searchQuery: String): List<Vendor> = withContext(workDispatcher) {
        api.getVendors()
            .filter { it.companyName.lowercase().contains(searchQuery.lowercase()) }
            .map { it.toVendor() }
    }

    private suspend fun NetworkVendor.toVendor() = coroutineScope {

        val categories = categories?.map {
            async {
                it.toCategory()
            }
        }

        val tags = tags?.let { tagsList ->
            async {
                buildString {
                    tagsList.forEach { tag ->
                        append(bullet)
                        append(" ")
                        append(tag.name)
                        append(" ")
                    }
                }
            }
        }

        Vendor(
            id = id,
            companyName = companyName,
            coverPhoto = coverPhoto.mediaUrl,
            area = areaServed,
            favorite = favorite,
            categories = categories?.awaitAll(),
            tags = tags?.await()
        )
    }

    private fun NetworkCategory.toCategory() =
        VendorCategory(
            id = id,
            title = name,
            imgUrl = image.mediaUrl
        )

}