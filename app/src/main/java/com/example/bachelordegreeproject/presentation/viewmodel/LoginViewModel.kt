package com.example.bachelordegreeproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelordegreeproject.core.nfc.NfcReader
import com.example.bachelordegreeproject.data.remote.repository.auth.AuthRepository
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityScoped
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.domain.models.AuthPerson
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ActivityScoped private val nfcReader: NfcReader,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _rfidStatus = MutableLiveData<RfidStatus>()
    val rfidStatus: LiveData<RfidStatus>
        get() = _rfidStatus

    private val _authResult = MutableLiveData<Result<AuthPerson>>()
    val authResult: LiveData<Result<AuthPerson>>
        get() = _authResult

    init {
        viewModelScope.launch {
            nfcReader.rfidIdStateFlow.collect { result ->
                _rfidStatus.postValue(result)
            }
        }
    }

    fun authPerson(login: String, password: String?) = viewModelScope.launch {
        _rfidStatus.postValue(RfidStatus.Idle)
        _authResult.postValue(Result.Loading)
        val result = authRepository.authPerson(login, password)
        _authResult.postValue(result)


        //TODO delete
        if (login == "admin") _authResult.postValue(Result.Success(AuthPerson("ok")))
    }
}