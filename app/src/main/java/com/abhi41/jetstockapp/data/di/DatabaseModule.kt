package com.abhi41.jetstockapp.data.di

import android.content.Context
import androidx.room.Room
import com.abhi41.jetstockapp.data.local.dao.StockDao
import com.abhi41.jetstockapp.data.local.database.StockDatabase
import com.abhi41.jetstockapp.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideStockDatabase(
        @ApplicationContext context: Context // dagger provide us context to use this for creation of builder
    ): StockDatabase {
        return Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideStockDao(database: StockDatabase): StockDao {
        return database.dao
    }
}