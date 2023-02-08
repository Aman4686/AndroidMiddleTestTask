package com.youarelaunched.challenge.ui.screen.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.middle.R
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

@Composable
fun VendorItem(
    vendor: Vendor,
    modifier: Modifier = Modifier
) {
    val vendorItemContentDescription = stringResource(id = R.string.cd_vendor_item)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = vendorItemContentDescription }
    ) {
        ShopPreview(
            url = vendor.coverPhoto,
            favorite = vendor.favorite,
            area = vendor.area
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Text(
            text = vendor.companyName,
            color = VendorAppTheme.colors.textDark,
            style = VendorAppTheme.typography.subtitle1
        )

        vendor.categories?.let { categories ->
            if (categories.isNotEmpty()) {
                Spacer(
                    modifier = Modifier.height(6.dp)
                )
                CategoriesLayout(
                    categories = categories
                )
            }
        }

        vendor.tags?.let { tags ->
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                modifier = modifier.fillMaxWidth(),
                text = tags,
                style = VendorAppTheme.typography.body2,
                lineHeight = 18.sp,
                color = VendorAppTheme.colors.text
            )
        }
    }
}

