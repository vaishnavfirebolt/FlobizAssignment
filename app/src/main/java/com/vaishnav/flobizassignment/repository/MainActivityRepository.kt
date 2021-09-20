package com.vaishnav.flobizassignment.repository

import com.vaishnav.flobizassignment.api.FlobizClient

object MainActivityRepository {

    private val api = FlobizClient.api
    suspend fun getMyData() = api.getData().body()?.response

}