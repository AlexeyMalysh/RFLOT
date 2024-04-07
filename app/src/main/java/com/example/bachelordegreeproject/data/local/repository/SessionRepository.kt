package com.example.bachelordegreeproject.data.local.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bachelordegreeproject.data.local.mappers.SessionEntityMapper
import com.example.bachelordegreeproject.database.dao.SessionDao
import com.example.bachelordegreeproject.domain.models.Session
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(
    private val sessionEntityMapper: SessionEntityMapper,
    private val sessionDao: SessionDao
) {

    private val _sessionState = MutableLiveData<Session?>()
    val sessionState: LiveData<Session?> = _sessionState

    suspend fun getSession(): Session = sessionState.value ?: loadSession()

    suspend fun setSession(session: Session) {
        val sessionEntity = sessionEntityMapper.reverse(session)
        sessionDao.setSession(sessionEntity)
        setSessionValue(session)
    }

    suspend fun deleteSessions() {
        sessionDao.deleteAllSessions()
        setSessionValue(null)
    }

    private suspend fun loadSession(): Session {
        val session = sessionDao.getSession().let(sessionEntityMapper::map)
        setSessionValue(session)
        return session
    }

    private fun setSessionValue(session: Session?) {
        _sessionState.postValue(session)
    }
}
