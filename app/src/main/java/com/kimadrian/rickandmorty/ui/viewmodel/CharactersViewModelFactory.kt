package com.kimadrian.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kimadrian.rickandmorty.data.repository.Repository

@Suppress("unchecked_cast")
class CharactersViewModelFactory(private val characterRepository: Repository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            return CharactersViewModel(characterRepository) as T
        }
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}