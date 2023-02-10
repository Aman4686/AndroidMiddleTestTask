package com.youarelaunched.challenge.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


abstract class BaseViewModel<T : UiState, in E : UiEvent> : ViewModel(){

    abstract val state: Flow<T>
}