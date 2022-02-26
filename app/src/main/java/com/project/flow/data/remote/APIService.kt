package com.project.flow.data.remote

import com.project.flow.data.remote.model.apiResponse
import com.project.flow.data.remote.model.character
import com.project.flow.util.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Federico Bal on 22/2/2022.
 */

interface APIService {

    @GET("api/character")
    suspend fun getListData(
        @Query("page") page: Int=0
    ): apiResponse

    @GET("api/character/{id}")
    suspend fun getItemData(@Path("id")id: Int): character

    companion object {

        private const val BASE_URL = Constant.BASE_URL

        operator fun invoke(): APIService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

}
