package com.abhi41.jetstockapp.data.csv

import java.io.InputStream

interface CSVParse<T> {
    suspend fun  parse(stream: InputStream): List<T>
}