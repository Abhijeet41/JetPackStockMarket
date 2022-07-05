package com.abhi41.jetstockapp.data.repository

import com.abhi41.jetstockapp.data.csv.CSVParser
import com.abhi41.jetstockapp.data.local.database.StockDatabase
import com.abhi41.jetstockapp.data.mapper.toCompanyInfo
import com.abhi41.jetstockapp.data.mapper.toCompanyListing
import com.abhi41.jetstockapp.data.mapper.toCompanyListingEntity
import com.abhi41.jetstockapp.data.remote.StockApi
import com.abhi41.jetstockapp.data.remote.dto.CompanyListing
import com.abhi41.jetstockapp.domain.model.CompanyInfo
import com.abhi41.jetstockapp.domain.model.IntradayInfo
import com.abhi41.jetstockapp.domain.repository.StockRepository
import com.abhi41.jetstockapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    //val companyListingsParser: CompanyListingsParser //don't depend on Concretion
    private val companyListingsParser: CSVParser<CompanyListing>, //depends on abstraction
    private val intradayInfoParser: CSVParser<IntradayInfo> //depends on abstraction
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map {
                    it.toCompanyListing()
                }
            ))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            //this is all for swipe to refresh fun
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListing()
                //response.byteStream() //It will allow us to read csv file
                companyListingsParser.parse(response.byteStream())

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            //if remoteListings not equals to null then read from database cache
            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map {
                        it.toCompanyListingEntity()
                    }
                )
                emit(Resource.Success(
                    data = dao.searchCompanyListing("").map {
                        it.toCompanyListing()
                    }
                ))
                emit(Resource.Loading(false))
            }

        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol = symbol)
            val results = intradayInfoParser.parse(response.byteStream())
            Resource.Success(results)

        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Could't load intraday info")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Could't load intraday info")
        }
    }

    override suspend fun getCompnayInfo(symbol: String): Resource<CompanyInfo> {
        try {
            val result = api.getCompanyInfo(symbol = symbol)
            return Resource.Success(result.toCompanyInfo())
        }catch (e: IOException) {
            e.printStackTrace()
           return Resource.Error("Could't load company info")
        } catch (e: HttpException) {
            e.printStackTrace()
          return  Resource.Error("Could't load company info")
        }
    }

}