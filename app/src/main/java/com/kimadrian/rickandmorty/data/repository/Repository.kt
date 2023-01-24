package com.kimadrian.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kimadrian.rickandmorty.data.model.characters.Result
import kotlinx.coroutines.flow.Flow

class Repository {
    fun getCharacters(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                PagingDataSource()
            }
        ).flow
    }
}