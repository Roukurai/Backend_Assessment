package com.example.backendassessment

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getResultsByQuery(@Url url:String):Response<QueryResponse>
}