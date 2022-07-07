package com.abhi41.jetstockapp.presentation.screen.compony_info

import com.abhi41.jetstockapp.domain.model.CompanyInfo
import com.abhi41.jetstockapp.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos:List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isloading:Boolean = false,
    val error:String? = null
)
