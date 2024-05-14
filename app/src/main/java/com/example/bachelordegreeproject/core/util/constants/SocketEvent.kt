package com.example.bachelordegreeproject.core.util.constants

import io.socket.client.Manager

enum class SocketEvent(val value: String) {
    EQUIPMENT_UPDATE("equipment_update"),
    CONNECT(Manager.EVENT_OPEN),
    DISCONNECT(Manager.EVENT_CLOSE)
}