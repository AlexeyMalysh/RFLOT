package com.example.bachelordegreeproject.core.network.socket

import com.example.bachelordegreeproject.core.network.socket.factory.SocketFactory
import com.example.bachelordegreeproject.core.util.constants.SocketEvent
import io.socket.client.Manager
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.serialization.json.Json
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketClient @Inject constructor(
    private val socketFactory: SocketFactory,
    private val loggingPrefix: String
) {
    private var socket: Socket? = null
    private val listeners = HashMap<String, Emitter.Listener>()
    private val socketListeners = HashMap<String, () -> Unit>()

    fun subscribe(token: String) {
        val socket = createSocket(token).also { socket = it } ?: return
        subscribeInternal(socket)
        socket.connect()
    }

    fun unsubscribe() {
        socket?.disconnect()
        socket = null
    }

    inline fun <reified T> on(event: SocketEvent, noinline listener: (T) -> Unit) {
        on(event.value, listener)
    }

    fun <T> on(event: String, listener: (T) -> Unit) {
        val wrappedListener = Emitter.Listener { values: Array<Any> ->
            values.forEach {
                log("$event: $it")

                val typedValue = if (it is JSONObject) {
                    Json.decodeFromString<Class<T>>(it.toString())
                } else {
                    null
                }

                if (typedValue != null) {
                    typedValue.cast(null)?.let { it1 -> listener.invoke(it1) }
                }
            }
        }

        listeners[event] = wrappedListener
        socket?.on(event, wrappedListener)
    }

    fun onSocketEvent(event: SocketEvent, listener: () -> Unit) {
        socketListeners[event.value] = listener
    }

    fun emitAppEvent(event: String, vararg args: Any) {
        listeners[event]?.call(*args)
    }

    fun emitSocketEvent(event: String) {
        socketListeners[event]?.invoke()
    }

    private fun createSocket(token: String): Socket? {
        // TODO
        return socketFactory.create("config.socketUri", token, "config.socketPathLocation")
    }

    private fun subscribeInternal(socket: Socket) {
        subscribeConnectionEvent(socket)

        listeners.forEach {
            socket.on(it.key, it.value)
        }
    }

    private fun subscribeConnectionEvent(socket: Socket) {
        arrayOf(
            Manager.EVENT_OPEN,
            Manager.EVENT_CLOSE,
            Manager.EVENT_ERROR
        ).forEach { event ->
            socket.io().on(event) {
                socketListeners[event]?.invoke()
                log(event)
            }
        }
    }

    private fun log(text: Any) {
        Timber.d("${loggingPrefix}_ $text")
    }
}