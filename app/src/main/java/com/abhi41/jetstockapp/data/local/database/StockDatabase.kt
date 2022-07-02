package com.abhi41.jetstockapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhi41.jetstockapp.data.local.dao.StockDao
import com.abhi41.jetstockapp.data.local.entity.CompanyListingEntity

@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class StockDatabase : RoomDatabase() {
    abstract val dao: StockDao
}