package com.example.bachelordegreeproject.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bachelordegreeproject.database.model.SessionEntity

@Dao
interface SessionDao {
    @Query("SELECT * FROM session LIMIT 1")
    suspend fun getSession(): SessionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSession(session: SessionEntity)

    @Query("DELETE FROM session")
    suspend fun deleteAllSessions()
}
