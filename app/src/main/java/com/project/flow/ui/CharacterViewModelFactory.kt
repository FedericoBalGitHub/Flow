package com.project.flow.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.flow.data.remote.APIService

/**
 * Created by Federico Bal on 22/2/2022.
 */

class CharacterViewModelFactory(
    private val api: APIService
) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            return CharacterViewModel(api) as T
        }else if(modelClass.isAssignableFrom((DetailCharacterViewModel::class.java)))
        {
            return DetailCharacterViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
