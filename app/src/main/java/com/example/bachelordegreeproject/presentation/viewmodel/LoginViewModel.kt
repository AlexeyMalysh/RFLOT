package com.example.bachelordegreeproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelordegreeproject.core.network.RflotHttpService
import com.example.bachelordegreeproject.core.nfc.NfcReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val nfcReader: NfcReader,
    private val rflotHttpService: RflotHttpService
) : ViewModel() {

    private val _rfidStatus = MutableLiveData<String>()
    val rfidStatus: LiveData<String>
        get() = _rfidStatus

    init {
        viewModelScope.launch {
            nfcReader.rfidIdStateFlow.collect { id ->
                _rfidStatus.postValue(id)
            }
        }
        nfcReader.startReadNfc()
    }

    override fun onCleared() {
        super.onCleared()
        nfcReader.stopReadNfc()
    }

    fun authorizePerson() {

    }
}