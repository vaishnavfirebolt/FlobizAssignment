package com.vaishnav.flobizassignment.api.services

import com.vaishnav.flobizassignment.api.models.DataResponse
import retrofit2.Response
import retrofit2.http.GET

interface FlobizApi {

    @GET("v1/890db937-1f45-496b-b1ac-ef43c259b8eb")
    suspend fun getData(): Response<DataResponse>
}