package com.example.compose.app

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Immutable
data class Contacts(
    val isLoading: Boolean,
    val names: List<String>
)

class AppViewModel : ViewModel() {

    val stateFlow = MutableStateFlow(Contacts(isLoading = false, listOf("Roman")))

    fun changeState(isLoading: Boolean) {
        stateFlow.value = stateFlow.value.copy(isLoading = isLoading)
    }

    fun changeState(names: List<String>) {
        stateFlow.value = stateFlow.value.copy(names = names)
    }

}