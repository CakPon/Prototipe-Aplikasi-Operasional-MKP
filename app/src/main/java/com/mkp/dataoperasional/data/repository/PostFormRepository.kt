package com.mkp.dataoperasional.data.repository

import PostFormRequest
import android.util.Log
import androidx.lifecycle.liveData
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.data.retrofit.ApiService
import retrofit2.HttpException


class PostFormRepository(private val apiService: ApiService) {
    fun postForm(request: PostFormRequest) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.postForm(request)
            emit(ResultState.Success(successResponse))
            Log.d("RestockSuccess", successResponse.toString())
        } catch (e: HttpException) {
            Log.e("HTTP Error", "HTTP Exception: ${e.message()}")
            emit(ResultState.Error("HTTP error: ${e.code()} - ${e.message()}"))
        } catch (e: Exception) {
            Log.e("Error", "Exception: ${e.message}")
            Log.e("Error", "Exception: ${e.message}")
            emit(ResultState.Error(e.message ?: "An error occurred"))
        }
    }

    companion object {
        @Volatile
        private var instance: PostFormRepository? = null

        fun getInstance(apiService: ApiService): PostFormRepository =
            instance ?: synchronized(this) {
                instance ?: PostFormRepository(apiService).also { instance = it }
            }
    }
}