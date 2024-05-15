
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer

import com.example.bachelordegreeproject.data.local.mappers.SessionEntityMapper
import com.example.bachelordegreeproject.data.local.repository.SessionRepository
import com.example.bachelordegreeproject.database.dao.SessionDao
import com.example.bachelordegreeproject.database.model.SessionEntity
import com.example.bachelordegreeproject.domain.models.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SessionRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var sessionEntityMapper: SessionEntityMapper
    @Mock
    private lateinit var sessionDao: SessionDao
    private lateinit var sessionRepository: SessionRepository
    private val testDispatcher = UnconfinedTestDispatcher()
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        sessionRepository = SessionRepository(sessionEntityMapper, sessionDao)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `getSession should return session from database`() = runBlocking {
        val sessionEntity = SessionEntity(0, "token", "userId", "planeId")
        val session = Session("token", "userId", "planeId")
        `when`(sessionDao.getSession()).thenReturn(sessionEntity)
        `when`(sessionEntityMapper.map(sessionEntity)).thenReturn(session)
        val result = sessionRepository.getSession()
        assert(result == session)
    }
    @Test
    fun `setSession should save session to database`() = runBlocking {
        val session = Session("token", "userId", "planeId")
        val sessionEntity = SessionEntity(0, "token", "userId", "planeId")
        `when`(sessionEntityMapper.reverse(session)).thenReturn(sessionEntity)
        sessionRepository.setSession(session)
        verify(sessionDao).setSession(sessionEntity)
    }

    @Test
    fun `deleteSessions should clear sessions from database`() = runBlocking {
        sessionRepository.deleteSessions()
        verify(sessionDao).deleteAllSessions()
    }

    @Test
    fun `sessionState should emit new value when session changes`() = runBlocking {
        val observer = mock(Observer::class.java) as Observer<Session?>
        sessionRepository.sessionState.observeForever(observer)
        val session = Session("token", "userId", "planeId")
        sessionRepository.setSession(session)
        verify(observer).onChanged(session)
    }
}

