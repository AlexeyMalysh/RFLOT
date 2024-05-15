package com.example.bachelordegreeproject.core.util.constants

object RflotUrl {
    val baseUrl: String
        get() = "10.147.17.151:5031"

    val authByLogin: String
        get() = "/auth/login-and-password"

    val authByRfid: String
        get() = "/auth/rfid"

    val authPlane: String
        get() = "/plane/start"

    val getZones: String
        get() = "/zone/get-zones"

    val checkZone: String
        get() = "zone/start-check"

    val closeZone: String
        get() = "zone/end-check"

    val checkExistEquip: String
        get() = "/equip/exit-check"

    val checkEquip: String
        get() = "/equip/check"

    //SOCKET
    val socketPath: String
        get() = "/hub"
}
