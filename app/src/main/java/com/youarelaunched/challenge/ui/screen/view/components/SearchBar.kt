package com.youarelaunched.challenge.ui.screen.view.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.middle.R
import com.youarelaunched.challenge.ui.screen.state.const.VendorsScreenConst
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

private const val TAG = "SearchBar"

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchClick: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    Card(
        modifier = modifier,
        elevation = 8.dp,
        backgroundColor = VendorAppTheme.colors.background,
        shape = MaterialTheme.shapes.medium
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearchQueryChanged(it)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchClick(searchQuery) }),
            textStyle = MaterialTheme.typography.subtitle2,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .padding(start = 14.dp, end = 12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = stringResource(R.string.search_bar_hint),
                                style = MaterialTheme.typography.subtitle2.copy(color = VendorAppTheme.colors.text)
                            )
                        }
                        innerTextField()
                    }
                    IconButton(onClick = {
                        onSearchClick(searchQuery)
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