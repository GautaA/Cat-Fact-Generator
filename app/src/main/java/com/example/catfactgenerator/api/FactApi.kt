package com.example.catfactgenerator.api

import retrofit2.Response
import retrofit2.http.GET

interface FactApi {

    @GET("/facts/random?animal_type=cat")
    suspend fun getFact() : Response<Fact>
}