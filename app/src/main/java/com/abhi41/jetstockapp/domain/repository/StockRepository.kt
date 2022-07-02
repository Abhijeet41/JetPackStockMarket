package com.abhi41.jetstockapp.domain.repository

import com.abhi41.jetstockapp.data.remote.dto.CompanyListing
import com.abhi41.jetstockapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    //we use flow because we get from cache so don't need to get resource
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean, //if its true then get data from remote otherwise database cache
        query: String
    ): Flow<Resource<List<CompanyListing>>>

}