package com.kimadrian.rickandmorty.data.network

import retrofit2.Response
import retrofit2.http.GET
import com.kimadrian.rickandmorty.data.model.characters.Characters
import com.kimadrian.rickandmorty.data.model.episodes.Episodes
import com.kimadrian.rickandmorty.data.model.locations.Locations

interface ApiService {
    @GET("character")
    suspend fun getCharacter(): Response<Characters>

    @GET("episode")
    suspend fun getEpisode(): Response<Episodes>

    @GET("location")
    suspend fun getLocation(): Response<Locations>
}