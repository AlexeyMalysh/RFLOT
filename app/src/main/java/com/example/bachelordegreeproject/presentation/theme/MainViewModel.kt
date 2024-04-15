package com.example.bachelordegreeproject.presentation.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelordegreeproject.core.nfc.NfcReader
import com.example.bachelordegreeproject.data.remote.repository.auth.AuthRepository
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ActivityScoped private val nfcReader: NfcReader,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _rfidStatus = MutableLiveData<RfidStatus>()
    val rfidStatus: LiveData<RfidStatus>
        get() = _rfidStatus

    init {
        viewModelScope.launch {
            nfcReader.rfidIdStateFlow.collect { result ->
                _rfidStatus.postValue(result)
            }
        }
    }
}
