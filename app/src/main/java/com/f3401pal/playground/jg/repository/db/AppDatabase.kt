package com.f3401pal.playground.jg.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.f3401pal.playground.jg.repository.db.dao.TransactionDao
import com.f3401pal.playground.jg.repository.db.entity.Transaction

private const val DB_VERSION = 1
private const val DB_NAME = "main_db"

@Database(
    entities = [ Transaction::class ],
    version = DB_VERSION,
)
@TypeConverters(
    LocalDataTimeConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    companion object {

        fun newInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .build()
        }
    }
}