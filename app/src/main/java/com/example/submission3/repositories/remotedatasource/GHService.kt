package com.example.submission3.repositories.remotedatasource

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GHService {
    private val servis: Retrofit? =
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val apiInstance = servis?.create(GHInterface::class.java)
}