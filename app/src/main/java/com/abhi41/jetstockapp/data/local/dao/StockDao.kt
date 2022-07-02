package com.abhi41.jetstockapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhi41.jetstockapp.data.local.entity.CompanyListingEntity

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntity: List<CompanyListingEntity>
    )

    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings()

    //note in sqlite || this symbol mean concatenation like we add + symbol in kotlin or java
    @Query(
        """
        SELECT * FROM companylistingentity
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR     
        UPPER(:query) == symbol
    """
    )
    suspend fun searchCompanyListing(query: String): List<CompanyListingEntity>
}