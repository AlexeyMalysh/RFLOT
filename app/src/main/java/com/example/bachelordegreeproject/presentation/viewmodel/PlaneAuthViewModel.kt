package com.example.bachelordegreeproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelordegreeproject.core.nfc.NfcReader
import com.example.bachelordegreeproject.core.util.constants.CheckType
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import com.example.bachelordegreeproject.core.util.constants.UiState
import com.example.bachelordegreeproject.data.remote.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaneAuthViewModel @Inject constructor(
    @ActivityScoped private val nfcReader: NfcReader,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _rfidStatus = MutableLiveData<RfidStatus>()
    val rfidStatus: LiveData<RfidStatus>
        get() = _rfidStatus

    private val _authPlaneResult = MutableLiveData<UiState>()
    val authPlaneResult: LiveData<UiState>
        get() = _authPlaneResult

    init {
        viewModelScope.launch {
            nfcReader.rfidIdStateFlow.collect { result ->
                _rfidStatus.postValue(result)
            }
        }
    }

    fun authPlane(planeId: String, typeCheck: CheckType) = viewModelScope.launch {
        _rfidStatus.postValue(RfidStatus.Idle)
        _authPlaneResult.postValue(UiState.Loading)
        val uiState = when (val result = authRepository.authPlane(planeId, typeCheck.value)) {
            is Result.Success -> {
                UiState.Success()
            }

            is Result.Fail -> {
                UiState.Error(result.text)
            }

            else -> {
                UiState.Idle
            }
        }
        _authPlaneResult.postValue(uiState)

        //TODO delete
        if (typeCheck == CheckType.PostflightCheck) _authPlaneResult.postValue(UiState.Success())
    }

    fun resetParams() {
        _authPlaneResult.postValue(UiState.Idle)
        _rfidStatus.postValue(RfidStatus.Idle)
    }
}