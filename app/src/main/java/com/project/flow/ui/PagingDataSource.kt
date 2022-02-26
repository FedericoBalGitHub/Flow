package com.project.flow.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.project.flow.data.remote.APIService
import com.project.flow.data.remote.model.apiResponse
import com.project.flow.data.remote.model.character

/**
 * Created by Federico Bal on 22/2/2022.
 */

class PagingDataSource(
    private val api: APIService
) : PagingSource<Int, character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, character> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response: apiResponse = api.getListData(nextPageNumber)
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.info.pages) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
//    override fun getRefreshKey(state: PagingState<Int, character>): Int? {
//        return state.anchorPosition?.let {
//            state.closestPageToPosition(it)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
//        }
//
//    }

}