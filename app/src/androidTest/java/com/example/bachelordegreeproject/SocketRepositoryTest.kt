package com.example.bachelordegreeproject

import com.example.bachelordegreeproject.core.network.ktor.RflotHttpClient
import com.example.bachelordegreeproject.core.network.ktor.RflotHttpServiceImpl
import com.example.bachelordegreeproject.core.network.socket.SocketClient
import com.example.bachelordegreeproject.core.util.constants.EquipStatus
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.core.util.constants.SocketEvent
import com.example.bachelordegreeproject.data.remote.mappers.EquipStateMapper
import com.example.bachelordegreeproject.data.remote.repository.socket.SocketRepository
import com.example.bachelordegreeproject.data.remote.request.AuthByLoginRequestModel
import com.example.bachelordegreeproject.data.remote.request.StartSessionRequestModel
import com.example.bachelordegreeproject.data.remote.response.AuthResponseModel
import com.example.bachelordegreeproject.data.remote.response.EquipStateResponseModel
import com.example.bachelordegreeproject.domain.models.EquipState
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SocketRepositoryTest {

    private lateinit var socketRepository: SocketRepository
    private lateinit var socketClient: SocketClient
    private lateinit var equipStateMapper: EquipStateMapper
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var coroutineDispatcher: CoroutineDispatcher
    private lateinit var httpClient: RflotHttpClient
    private lateinit var httpService: RflotHttpServiceImpl

    @Before
    fun setUp() {
        socketClient = mockk()
        equipStateMapper = mockk()
        httpClient = mockk()
        coroutineDispatcher = Dispatchers.IO
        coroutineScope = CoroutineScope(Dispatchers.Main + Job())
        socketRepository = SocketRepository(socketClient, equipStateMapper, coroutineScope)
        httpService = RflotHttpServiceImpl(httpClient, coroutineDispatcher)
    }

    @Test
    fun startSessionFailedStatus() = runBlocking {
        val authParams = AuthByLoginRequestModel("user", "pass")
        val expectedResponse = AuthResponseModel("token")
        val result = httpService.authByLogin(authParams)

        assertEquals(Result.Success(expectedResponse), result)
    }


    @Test
    fun getZoneInfoOkExecution() = runBlocking {
        val authParams = AuthByLoginRequestModel("user", "pass")
        val expectedResponse = AuthResponseModel("token")
        val result = httpService.authByLogin(authParams)

        assertEquals(Result.Success(expectedResponse), result)
    }

    @Test
    fun getPlaneOkExecution() = runBlocking {
        val authParams = StartSessionRequestModel("user", "pass")
        val exception = Exception("Network error")

        val result = httpService.startSession(authParams)

        assertEquals(Result.Fail(exception), result)
    }

    @Test
    fun authPersonWrongExecution() = runBlocking {
        val authParams = AuthByLoginRequestModel("user", "pass")
        val exception = Exception("Network error")

        val result = httpService.authByLogin(authParams)

        assertEquals(Result.Fail(exception), result)
    }

    @Test
    fun testSubscribeAndUnsubscribe() = runBlocking {
        every { socketClient.subscribe("") } just Runs
        every { socketClient.unsubscribe() } just Runs

        val isSubscribed = socketRepository.subscribe()
        val isUnsubscribed = socketRepository.unsubscribe()

        assertTrue(isSubscribed)
        assertTrue(isUnsubscribed)
        verify { socketClient.subscribe("") }
        verify { socketClient.unsubscribe() }
    }

    @Test
    fun testEquipmentUpdateSocketFlow() = runBlocking {
        val equipStateResponseModel = EquipStateResponseModel("A1", 2)
        val equipState = EquipState("A1", EquipStatus.UNKNOWN)
        every { equipStateMapper.map(equipStateResponseModel) } returns equipState
        every { socketClient.onSocketEvent(SocketEvent.CONNECT, any()) } just Runs
        every { socketClient.onSocketEvent(SocketEvent.DISCONNECT, any()) } just Runs

        launch {
            socketRepository.equipmentUpdateSocketFlow.collect { }
        }
        socketRepository.onEquipmentUpdate(equipStateResponseModel)

        verify { equipStateMapper.map(equipStateResponseModel) }
    }

    @Test
    fun testSocketConnectAndDisconnect() = runBlocking {
        every { socketClient.onSocketEvent(SocketEvent.CONNECT, any()) } just Runs
        every { socketClient.onSocketEvent(SocketEvent.DISCONNECT, any()) } just Runs

        socketRepository.onSocketConnect()
        assertTrue(socketRepository.isPickerSocketConnectBroken)

        socketRepository.onSocketConnect()
        assertFalse(socketRepository.isPickerSocketConnectBroken)
        verify(exactly = 2) { socketRepository.equipmentSocketConnectEvent.tryEmit(Unit) }
    }
}
