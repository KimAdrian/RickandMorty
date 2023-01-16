package com.kimadrian.rickandmorty.data.repository

import com.kimadrian.rickandmorty.data.network.RetrofitInstance

class Repository {
    suspend fun getCharacters() = RetrofitInstance.apiService.getCharacter()

    suspend fun getEpisodes() = RetrofitInstance.apiService.getEpisode()

    suspend fun getLocations() = RetrofitInstance.apiService.getLocation()
}