package com.project.flow.data


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.project.flow.data.remote.APIService
import com.project.flow.data.remote.model.apiResponse
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth

/**
 * Created by fbal on 24/2/2022.
 */

@RunWith(AndroidJUnit4::class)
class ListRemoteTest : TestCase() {

    private lateinit var api: APIService

    @Before
    public override fun setUp() {
        val context= ApplicationProvider.getApplicationContext<Context>()
        api= APIService()
    }

    @After
    public override fun tearDown() {
    }

    @Test
    fun getCharactersData()= runBlocking {
        val page:Int=0
        var resp:apiResponse
        val result =api.getListData(page)
        Truth.assertThat(result.results.isNotEmpty()).isTrue()
    }
}