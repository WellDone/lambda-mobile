package com.versilistyson.welldone.data.api

import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LogApi {

    @GET("api/log/{sensorId}")
    suspend fun getLogs(@Path("sensorId") sensorId: Long): Response<List<Dto.Log>>

    sealed class Dto {
        data class Log(
            @Json(name = "log_index") val logId: Long,
            @Json(name = "date_filed") val dateFiled: String,
            @Json(name = "last_modified") val lastModified: String,
            @Json(name = "status") val status: String,
            @Json(name = "comment") val comment: String,
            @Json(name = "pictures") val pictures: MutableList<String>
        )
    }

}