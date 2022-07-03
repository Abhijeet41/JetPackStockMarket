package com.abhi41.jetstockapp.presentation.screen.compony_listings

sealed class CompanyListingsEvent{
    object Refresh: CompanyListingsEvent()
    data class OnSearchQueryChange(val query: String): CompanyListingsEvent()
}
