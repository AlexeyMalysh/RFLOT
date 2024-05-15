package com.example.bachelordegreeproject.core.network.socket.factory

import com.example.bachelordegreeproject.core.util.constants.RflotUrl
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import timber.log.Timber

interface SocketFactory {
    fun create(uri: String, token: String, socketPathLocation: String): Socket?

    abstract class Base : SocketFactory {
        protected fun createSocket(
            uri: String,
            token: String,
            socketPath: String
        ): Socket? {
            try {
                val options = IO.Options().apply {
                    transports = arrayOf(WebSocket.NAME)
                    path = socketPath
                    extraHeaders = mapOf("App-token" to listOf(token))
                }
                return IO.socket(uri, options)
            } catch (e: Exception) {
                Timber.e(e.message)
            }
            return null
        }
    }
    class Hostess : Base() {
        override fun create(uri: String, token: String, socketPathLocation: String): Socket? {
            return createSocket(
                uri = uri,
                token = token,
                socketPath = "${socketPathLocation}${RflotUrl.socketPath}"
            )
        }

        companion object {
            const val LOGGING_PREFIX = "client"
        }
    }
}
