package com.example.bachelordegreeproject.core.util.constants

object RflotUrl {
    val baseUrl: String
        get() = "openlibrary.org"

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

    val checkEquip: String
        get() = "/check-equip"

    //SOCKET
    val socketPath: String
        get() = "/hub"
}
