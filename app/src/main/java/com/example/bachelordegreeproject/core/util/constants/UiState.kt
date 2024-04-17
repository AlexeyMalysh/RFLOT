package com.example.bachelordegreeproject.core.util.constants

sealed class UiState {
    data object Loading : UiState()
    data object Idle : UiState()
    data class Success(val text: String? = null) : UiState()
    data class Error(val text: String? = null) : UiState()
}
