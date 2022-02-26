package com.project.flow.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.project.flow.data.remote.APIService

/**
 * Created by Federico Bal on 22/2/2022.
 */

class CharacterViewModel(
    private val api: APIService
) : ViewModel() {
    val characters =
        Pager(
                config = PagingConfig(pageSize = 10, enablePlaceholders = false),
                pagingSourceFactory = {
                    PagingDataSource(api)
                }
            ).
            flow.
            cachedIn(viewModelScope)


}
