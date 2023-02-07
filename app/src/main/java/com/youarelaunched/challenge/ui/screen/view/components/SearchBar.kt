package com.youarelaunched.challenge.ui.screen.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.middle.R
import com.youarelaunched.challenge.ui.screen.view.VendorsVM
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    var value by remember {
        mutableStateOf("")
    }

    Card(
        modifier = modifier,
        elevation = 8.dp,
        backgroundColor = VendorAppTheme.colors.background,
        shape = MaterialTheme.shapes.medium
    ) {
        BasicTextField(
            value = value,
            onValueChange = { value = it },
            textStyle = MaterialTheme.typography.subtitle2,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .padding(start = 14.dp, end = 12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = "Search...",
                                style = MaterialTheme.typography.subtitle2.copy(color = VendorAppTheme.colors.text)
                            )
                        }
                        innerTextField()
                    }
                    IconButton(onClick = {
                        onSearch(value)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = "Search Icon",
                        )
                    }
                }
            })
    }
}