package com.example.bachelordegreeproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bachelordegreeproject.database.dao.SessionDao
import com.example.bachelordegreeproject.database.model.SessionEntity

@Database(entities = [SessionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "rflot_database"
        fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }

    abstract fun sessionDao(): SessionDao
}
