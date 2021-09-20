package com.vaishnav.flobizassignment.api

import com.vaishnav.flobizassignment.api.services.FlobizApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object FlobizClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://mocki.io/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api: FlobizApi by lazy {
        retrofit.create(FlobizApi::class.java)
    }
}