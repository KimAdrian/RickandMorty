package com.kimadrian.rickandmorty.data.network

import retrofit2.Response
import retrofit2.http.GET
import com.kimadrian.rickandmorty.data.model.Character

interface ApiService {
    @GET("character")
    suspend fun getCharacter(): Response<Character>
}