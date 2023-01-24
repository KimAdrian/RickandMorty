package com.kimadrian.rickandmorty.data.repository
import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kimadrian.rickandmorty.data.model.characters.Result
import com.kimadrian.rickandmorty.data.network.RetrofitInstance

class PagingDataSource : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPage = params.key ?: 1
            val response = RetrofitInstance.apiService.getCharacter(nextPage)
            var nextPageNumber : Int? = null

            if (response.body()?.info != null){
                val uri = Uri.parse(response.body()?.info?.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = response.body()!!.results,
                prevKey = null,
                nextKey = nextPageNumber)

        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }
}