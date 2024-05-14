package com.example.bachelordegreeproject.presentation.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelordegreeproject.core.nfc.NfcReader
import com.example.bachelordegreeproject.core.util.constants.EquipStatus
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import com.example.bachelordegreeproject.core.util.constants.UiState
import com.example.bachelordegreeproject.core.util.constants.observe
import com.example.bachelordegreeproject.data.remote.repository.auth.AuthRepository
import com.example.bachelordegreeproject.data.remote.repository.equip.EquipRepository
import com.example.bachelordegreeproject.data.remote.repository.socket.SocketRepository
import com.example.bachelordegreeproject.data.remote.repository.zones.ZonesRepository
import com.example.bachelordegreeproject.domain.models.EquipInfo
import com.example.bachelordegreeproject.domain.models.EquipState
import com.example.bachelordegreeproject.domain.models.Zones
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    @ActivityScoped private val nfcReader: NfcReader,
    private val authRepository: AuthRepository,
    private val zonesRepository: ZonesRepository,
    private val equipRepository: EquipRepository,
    private val socketRepository: SocketRepository,
    @Named("alarmIntent") private val alarmIntent: Intent
) : ViewModel() {
    private val _rfidStatus = MutableLiveData<RfidStatus>()
    val rfidStatus: LiveData<RfidStatus>
        get() = _rfidStatus

    private val _authResult = MutableLiveData<UiState>()
    val authResult: LiveData<UiState>
        get() = _authResult

    private val _authPlaneResult = MutableLiveData<UiState>()
    val authPlaneResult: LiveData<UiState>
        get() = _authPlaneResult

    private val _zoneList = MutableLiveData<Result<Zones>>()
    val zoneList: LiveData<Result<Zones>>
        get() = _zoneList

    private val _equipsList = MutableLiveData<List<EquipState>>()
    val equipsList: LiveData<List<EquipState>>
        get() = _equipsList

    private val _equipExist = MutableLiveData<EquipInfo?>()
    val equipExist: LiveData<EquipInfo?>
        get() = _equipExist

    private val _playSound = MutableLiveData<Intent?>()
    val playSound: LiveData<Intent?>
        get() = _playSound

    private val _zoneName = MutableLiveData<UiState>()
    val zoneName: LiveData<UiState>
        get() = _zoneName

    init {
        enableSocketListeners()
        viewModelScope.launch {
            nfcReader.rfidIdSharedFlow.collect { result ->
                _rfidStatus.postValue(result)
            }
        }
//        val equip: List<EquipState> = listOf(
//            EquipState("A1", EquipStatus.OK),
//            EquipState("A2", EquipStatus.UNKNOWN),
//            EquipState("A3", EquipStatus.UNKNOWN),
//            EquipState("A4", EquipStatus.UNKNOWN),
//            EquipState("B1", EquipStatus.UNKNOWN),
//            EquipState("B2", EquipStatus.UNKNOWN),
//            EquipState("B3", EquipStatus.UNKNOWN),
//            EquipState("B4", EquipStatus.UNKNOWN),
//            EquipState("C1", EquipStatus.UNKNOWN),
//            EquipState("C2", EquipStatus.UNKNOWN),
//            EquipState("C3", EquipStatus.UNKNOWN),
//            EquipState("C4", EquipStatus.UNKNOWN),
//            EquipState("D1", EquipStatus.DateFail),
//            EquipState("D2", EquipStatus.OK),
//            EquipState("D3", EquipStatus.OK),
//            EquipState("D4", EquipStatus.OK),
//        )
//        _equipsList.postValue(equip)
    }

    // TODO delete
//    var count = 0
//    var lastRfid: String? = null
//    fun convertEquipsList() {
//        val equips = _equipsList.value?.toMutableList() ?: return
//        if (count == 0 && _rfidStatus.value is RfidStatus.Success && (_rfidStatus.value as RfidStatus.Success).rfid != lastRfid) {
//            val equip = equips.find { it.space == "A2" } ?: return
//            val index = equips.indexOf(equip)
//            equips[index] = equip.copy(status = EquipStatus.OK)
//            _equipsList.postValue(equips)
//        } else if (count == 1 && _rfidStatus.value is RfidStatus.Success && (_rfidStatus.value as RfidStatus.Success).rfid != lastRfid) {
//            val equip = equips.find { it.space == "A3" } ?: return
//            val index = equips.indexOf(equip)
//            equips[index] = equip.copy(status = EquipStatus.DateFail)
//            _equipsList.postValue(equips)
//        } else if (count == 2 && _rfidStatus.value is RfidStatus.Success && (_rfidStatus.value as RfidStatus.Success).rfid != lastRfid) {
//            val equip = equips.find { it.space == "A4" } ?: return
//            val index = equips.indexOf(equip)
//            equips[index] = equip.copy(status = EquipStatus.DateFail)
//            _equipsList.postValue(equips)
//        }
//        if (_rfidStatus.value is RfidStatus.Success) lastRfid =
//            (_rfidStatus.value as RfidStatus.Success).rfid
//        count++
//    }

    fun authPersonByLogin(login: String, password: String?) = viewModelScope.launch {
        _rfidStatus.postValue(RfidStatus.Idle)
        _authResult.postValue(UiState.Loading)
        val uiState = when (val result = authRepository.authByLogin(login, password)) {
            is Result.Success -> UiState.Success()
            is Result.Fail -> UiState.Error(result.text)
            else -> {
                UiState.Idle
            }
        }
        _authResult.postValue(uiState)
    }

    fun authPersonByRfid(rfid: String) = viewModelScope.launch {
        _rfidStatus.postValue(RfidStatus.Idle)
        _authResult.postValue(UiState.Loading)
        val uiState = when (val result = authRepository.authByRfid(rfid)) {
            is Result.Success -> UiState.Success()
            is Result.Fail -> UiState.Error(result.text)
            else -> {
                UiState.Idle
            }
        }
        _authResult.postValue(uiState)
    }

    fun authPlane(planeId: String, typeCheck: String) = viewModelScope.launch {
        _rfidStatus.postValue(RfidStatus.Idle)
        _authPlaneResult.postValue(UiState.Loading)
        val uiState = when (val result = authRepository.authPlane(planeId, typeCheck)) {
            is Result.Success -> {
                UiState.Success(planeId)
            }

            is Result.Fail -> {
                UiState.Error(result.text)
            }

            else -> {
                UiState.Idle
            }
        }
        _authPlaneResult.postValue(uiState)

        delay(3500)
        _authPlaneResult.postValue(UiState.Success(planeId))
    }

    fun getAllZones() = viewModelScope.launch {
        _rfidStatus.postValue(RfidStatus.Idle)
        _zoneList.postValue(Result.Loading)
        val result = zonesRepository.getAllZones()
        _zoneList.postValue(result)
    }

    fun startCheckZone(zoneId: String) = viewModelScope.launch {
        _zoneName.postValue(UiState.Loading)
        _equipsList.postValue(listOf())
        val result = zonesRepository.startCheckZone(zoneId)
        val equipList = if (result is Result.Success) {
            _zoneName.postValue(UiState.Success(result.value.zoneName))
            result.value.spaces.map {
                val findEquipStatus =
                    result.value.equip.find { equip -> equip.space == it }?.status
                        ?: EquipStatus.UNKNOWN
                EquipState(it, findEquipStatus)
            }
        } else {
            _zoneName.postValue(UiState.Error())
            listOf()
        }
        _equipsList.postValue(equipList)
    }

    fun checkExistEquipment() {
        if (_rfidStatus.value is RfidStatus.Success) {
            viewModelScope.launch {
                val result =
                    equipRepository.checkEquip((_rfidStatus.value as RfidStatus.Success).rfid)
                if (result is Result.Success) {
                    result.value.let { _equipExist.postValue(it) }
                    _playSound.postValue(alarmIntent)
                } else {
                    _playSound.postValue(null)
                }
                _rfidStatus.postValue(RfidStatus.Idle)
            }
            // TODO delete
            _equipExist.postValue(EquipInfo("Инструкция", "A2", "", ""))
            _playSound.postValue(alarmIntent)
            //
        }
    }

    fun resetParams() {
        _authResult.postValue(UiState.Idle)
        _authPlaneResult.postValue(UiState.Idle)
        _zoneList.postValue(Result.Empty)
        _rfidStatus.postValue(RfidStatus.Idle)
        _equipExist.postValue(null)
        _playSound.postValue(null)
    }

    private fun enableSocketListeners() {
        socketRepository.equipmentUpdateEvent.observe(viewModelScope, ::handleEquipmentUpdate)
        socketRepository.pickerSocketConnectEvent.observe(viewModelScope) { resetParams() }
    }

    private fun handleEquipmentUpdate(equipState: EquipState) {
        val newList =
            _equipsList.value?.map { if (equipState.space == it.space) equipState else it }
                ?: return
        _equipsList.postValue(newList)
    }
}
