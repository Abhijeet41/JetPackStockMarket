package com.abhi41.jetstockapp.data.mapper

import com.abhi41.jetstockapp.data.local.entity.CompanyListingEntity
import com.abhi41.jetstockapp.data.remote.dto.CompanyListing

//every layer has allow to access that and data layer
fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}