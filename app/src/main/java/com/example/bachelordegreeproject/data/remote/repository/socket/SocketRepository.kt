package com.example.bachelordegreeproject.data.remote.repository.socket

import com.example.bachelordegreeproject.core.network.socket.SocketClient
import com.example.bachelordegreeproject.core.util.constants.MultipleEventFlow
import com.example.bachelordegreeproject.core.util.constants.SocketEvent
import com.example.bachelordegreeproject.data.remote.mappers.EquipStateMapper
import com.example.bachelordegreeproject.data.remote.response.EquipStateResponseModel
import com.example.bachelordegreeproject.domain.models.EquipState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketRepository @Inject constructor(
    private val socketClient: SocketClient,
    private val equipmentStateMapper: EquipStateMapper,
    private val coroutineScope: CoroutineScope
) {
    private var isSubscribed = false
    private var isPickerSocketConnectBroken = false

    private val equipmentUpdateSocketFlow = MultipleEventFlow<EquipStateResponseModel>()
    val pickerSocketConnectEvent = MultipleEventFlow<Unit>()
    val equipmentUpdateEvent = MultipleEventFlow<EquipState>()

    init {
        socketClient.on(SocketEvent.EQUIPMENT_UPDATE, ::onEquipmentUpdate)
        socketClient.onSocketEvent(SocketEvent.CONNECT, ::onSocketConnect)
        socketClient.onSocketEvent(SocketEvent.DISCONNECT) { isPickerSocketConnectBroken = true }

        launchEquipmentUpdateSocketFlow()
    }

    fun subscribe(): Boolean {
        if (isSubscribed) return false
        socketClient.subscribe("")
        isSubscribed = true
        isPickerSocketConnectBroken = false
        return true
    }

    fun unsubscribe(): Boolean {
        if (!isSubscribed) return false
        socketClient.unsubscribe()
        isSubscribed = false
        return true
    }

    // Used for tests
    fun emitEquipmentUpdate(event: EquipStateResponseModel) {
        equipmentUpdateSocketFlow.tryEmit(event)
    }

    private fun launchEquipmentUpdateSocketFlow() {
        equipmentUpdateSocketFlow
            .map(equipmentStateMapper::map)
            .flowOn(Dispatchers.IO)
            .onEach { equipmentUpdateEvent.emit(it) }
            .flowOn(Dispatchers.Main)
            .launchIn(coroutineScope)
    }

    private fun onEquipmentUpdate(equipment: EquipStateResponseModel) {
        equipmentUpdateSocketFlow.tryEmit(equipment)
    }

    private fun onSocketConnect() {
        if (isPickerSocketConnectBroken) {
            isPickerSocketConnectBroken = false
            pickerSocketConnectEvent.tryEmit(Unit)
        }
    }
}
