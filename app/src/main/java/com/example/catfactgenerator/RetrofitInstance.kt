package com.example.catfactgenerator

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.xml.parsers.FactoryConfigurationError

object RetrofitInstance {

    val api: FactApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://cat-fact.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FactApi::class.java)
    }
}