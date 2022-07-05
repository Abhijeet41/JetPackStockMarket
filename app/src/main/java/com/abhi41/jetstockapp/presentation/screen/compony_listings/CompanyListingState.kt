package com.abhi41.jetstockapp.presentation.screen.compony_listings

import com.abhi41.jetstockapp.data.remote.dto.CompanyListing


data class CompanyListingState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefresing: Boolean = false,
    val searchQuery: String = ""
)
