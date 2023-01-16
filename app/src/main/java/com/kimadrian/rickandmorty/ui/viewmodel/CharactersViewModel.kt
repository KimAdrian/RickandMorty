package com.kimadrian.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.kimadrian.rickandmorty.utils.Resource
import com.kimadrian.rickandmorty.data.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber


class CharactersViewModel(private val repository: Repository): ViewModel() {

    fun getCharacters() = CoroutineScope(Dispatchers.IO).launch {
        flow {
            emit(Resource.loading(data = null))
            try {
                val characterResponse = repository.getCharacters()
                if (characterResponse.isSuccessful && characterResponse.body() != null){
                    emit(Resource.success(data = characterResponse.body()?.results))
                }
            } catch (e: HttpException) {
                Timber.e("HttpException: ${e.message}")
                emit(Resource.error(data = null, message = "HttpException: ${e.message}"))
            } catch (e: ClassCastException) {
                Timber.e( "ClassCastException: ${e.message}")
            } catch (e: Exception) {
                Timber.e( "Exception: ${e.message}")
                emit(Resource.error(data = null, message = "Exception: ${e.message}"))
            }

        }
    }

}