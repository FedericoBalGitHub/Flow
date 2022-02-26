package com.project.flow.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.flow.data.remote.APIService
import com.project.flow.data.remote.model.character
import com.project.flow.util.Resource
import kotlinx.coroutines.launch

/**
 * Created by Federico Bal on 24/2/2022.
 */

class DetailCharacterViewModel(
    private val api: APIService
) : ViewModel() {
    private val _characterResponse = MutableLiveData<Resource<character>>()
    val characterResponse: LiveData<Resource<character>> = _characterResponse

    fun getCharacter(id:Int) {
        Log.d("FLOW", "getCharacter")
        _characterResponse.value = Resource.Loading
        viewModelScope.launch {
            try {
                var character:character = api.getItemData(id)
                _characterResponse.value = Resource.Success(character)
            }
            catch (ex:Exception)
            {
                _characterResponse.value = Resource.Failure(ex)
            }
        }
    }
}
