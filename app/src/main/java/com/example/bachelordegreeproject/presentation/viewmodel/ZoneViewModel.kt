package com.example.bachelordegreeproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelordegreeproject.core.nfc.NfcReader
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import com.example.bachelordegreeproject.data.remote.repository.zones.ZonesRepository
import com.example.bachelordegreeproject.domain.models.ZoneInfo
import com.example.bachelordegreeproject.domain.models.Zones
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZoneViewModel @Inject constructor(
    @ActivityScoped private val nfcReader: NfcReader,
    private val zonesRepository: ZonesRepository
) : ViewModel() {
    private val _rfidStatus = MutableLiveData<RfidStatus>()
    val rfidStatus: LiveData<RfidStatus>
        get() = _rfidStatus

    private val _zoneList = MutableLiveData<Result<Zones>>()
    val zoneList: LiveData<Result<Zones>>
        get() = _zoneList

    init {
        viewModelScope.launch {
            nfcReader.rfidIdStateFlow.collect { result ->
                _rfidStatus.postValue(result)
            }
        }
    }

    fun getAllZones() = viewModelScope.launch {
        _rfidStatus.postValue(RfidStatus.Idle)
        _zoneList.postValue(Result.Loading)
        val result = zonesRepository.getAllZones()
        _zoneList.postValue(result)

        //TODO delete
        _zoneList.postValue(
            Result.Success(
                Zones(
                    listOf(
                        ZoneInfo("Эконом класс", listOf("Марат Башаров", "Чи Ли")),
                        ZoneInfo(
                            "Бизнес класс",
                            listOf("Васы Пупки")
                        )
                    )
                )
            )
        )
    }

    fun resetParams() {
        _zoneList.postValue(Result.Empty)
        _rfidStatus.postValue(RfidStatus.Idle)
    }
}