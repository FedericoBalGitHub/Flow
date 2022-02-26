package com.project.flow.util


/**
 * Created by Federico Bal on 24/2/2022.
 */

sealed class Resource<out T> {
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val exception: Exception) : Resource<Nothing>()
}