package com.abhi41.jetstockapp.data.di

import com.abhi41.jetstockapp.data.csv.CSVParser
import com.abhi41.jetstockapp.data.csv.CompanyListingsParser
import com.abhi41.jetstockapp.data.csv.IntradayInfoParser
import com.abhi41.jetstockapp.data.remote.dto.CompanyListing
import com.abhi41.jetstockapp.data.repository.StockRepositoryImpl
import com.abhi41.jetstockapp.domain.model.IntradayInfo
import com.abhi41.jetstockapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    //this is how we provide interface
    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindCompanyIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoRepository(
        stockRepositoryImpl: IntradayInfoParser
    ):StockRepository
}