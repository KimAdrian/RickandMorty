package com.kimadrian.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kimadrian.rickandmorty.data.model.characters.Result
import com.kimadrian.rickandmorty.data.repository.Repository
import kotlinx.coroutines.flow.Flow


class CharactersViewModel(private val repository: Repository): ViewModel() {

    fun getCharacters(): Flow<PagingData<Result>> = repository.getCharacters().cachedIn(viewModelScope)


}