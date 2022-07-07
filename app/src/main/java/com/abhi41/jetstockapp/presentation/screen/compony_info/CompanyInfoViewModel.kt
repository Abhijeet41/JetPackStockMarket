package com.abhi41.jetstockapp.presentation.screen.compony_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.jetstockapp.domain.repository.StockRepository
import com.abhi41.jetstockapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isloading = true)
            //note we put in async because we want to run api's simultaneously instead of one after another
            val companyInfoResult = async { repository.getCompnayInfo(symbol) }
            val intradayInfoResult = async { repository.getIntradayInfo(symbol) }
            // Note Awaits for completion of this value without blocking a thread
            when (val result = companyInfoResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        company = result.data,
                        isloading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isloading = false,
                        error = result.message,
                        company = null
                    )
                }
                else -> Unit
            }

            /* ------------Intraday Info------------*/

            when (val result = intradayInfoResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        stockInfos = result.data ?: emptyList(),
                        isloading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isloading = false,
                        error = result.message,
                        stockInfos = emptyList()
                    )
                }
                else -> Unit
            }

        }
    }

}