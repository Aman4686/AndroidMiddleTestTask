package com.youarelaunched.challenge.utils

import com.youarelaunched.challenge.ui.screen.state.const.VendorsScreenConst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(
    waitMs: Long = VendorsScreenConst.AUTO_SEARCH_DEBOUNCE_MS,
    scope: CoroutineScope,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(waitMs)
            action(param)
        }
    }
}
