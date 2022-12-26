package com.kimadrian.rickandmorty.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val Base_URL = "https://rickandmortyapi.com/api/"
private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

object RetrofitInstance {
    val apiService: ApiService by lazy {
        Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Base_URL)
            .build()
            .create(ApiService::class.java)
    }
}