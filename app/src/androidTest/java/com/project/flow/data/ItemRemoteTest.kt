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
import com.project.flow.data.remote.model.character
import com.project.flow.util.Resource

/**
 * Created by fbal on 24/2/2022.
 */

@RunWith(AndroidJUnit4::class)
class ItemRemoteTest : TestCase() {

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
    fun getCharactersDataOk()= runBlocking {
        val page:Int=2
        var resp:apiResponse
        var result: character? = null
        var error:Boolean=false
        try {
            result = api.getItemData(page)
        }
        catch(ex:Exception)
        {
        }
        Truth.assertThat(result != null).isTrue()
    }
    @Test
    fun getCharactersDataError()= runBlocking {
        val page:Int=9999
        var resp:apiResponse
        var result: character? = null
        var error:Boolean=false
        try {
            result = api.getItemData(page)
        }
        catch(ex:Exception)
        {
        }
        Truth.assertThat(result == null).isTrue()
    }
}