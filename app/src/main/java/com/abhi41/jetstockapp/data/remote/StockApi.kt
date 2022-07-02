package com.abhi41.jetstockapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListing(
        @Query("apikey") apikey: String
    ):ResponseBody  //this is for download csv file

    companion object{
     //   const val API_KEY = "NV6MZ63JZOR6W1FA" //my key
        const val API_KEY = "Q63Y9NX3TUF587NF"
        const val BASE_URL = "https://alphavantage.co"
    }
}