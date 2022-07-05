package com.abhi41.jetstockapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.abhi41.jetstockapp.data.remote.dto.IntradayInfoDto
import com.abhi41.jetstockapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun IntradayInfoDto.toIntradayInfo(): IntradayInfo{
    val pattern = "dd-MM-yyy HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp,formatter)
    return IntradayInfo(
        date = localDateTime,
        close = close
    )
}