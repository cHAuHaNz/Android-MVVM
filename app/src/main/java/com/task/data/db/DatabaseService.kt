package com.task.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.data.db.dao.DummyDao
import com.task.data.db.entity.DummyEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        DummyEntity::class
    ],
    exportSchema = false,
    version = 1
)
abstract class DatabaseService : RoomDatabase() {
    abstract fun dummyDao(): DummyDao
}