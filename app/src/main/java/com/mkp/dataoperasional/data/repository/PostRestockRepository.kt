package com.mkp.dataoperasional.data.repository

import PostOneRowRequest
import PostRequest
import android.util.Log
import androidx.lifecycle.liveData
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.data.retrofit.ApiService
import retrofit2.HttpException

class PostRestockRepository(private val apiService: ApiService) {
    fun postRestockClothesWearPack(request: PostRequest) = liveData {
        emit(ResultState.Loading) // Emit loading state before the network call
        try {
            val successResponse = apiService.postRestockClothesWearPack(request) // This is CommonResponse
            emit(ResultState.Success(successResponse))
            Log.d("RestockSuccess", successResponse.toString())
        } catch (e: HttpException) {
            // Handle HTTP exceptions
            Log.e("HTTP Error", "HTTP Exception: ${e.message()}")
            emit(ResultState.Error("HTTP error: ${e.code()} - ${e.message()}"))
        } catch (e: Exception) {
            // Handle any other exceptions
            Log.e("Error", "Exception: ${e.message}")
            Log.e("Error", "Exception: ${e.message}")
            emit(ResultState.Error(e.message ?: "An error occurred"))
        }
    }

    fun postRestockPantsWearPack(request: PostRequest) = liveData {
        emit(ResultState.Loading) // Emit loading state before the network call
        try {
            val successResponse = apiService.postRestockPantsWearPack(request) // This is CommonResponse
            emit(ResultState.Success(successResponse))
            Log.d("RestockSuccess", successResponse.toString())
        } catch (e: HttpException) {
            // Handle HTTP exceptions
            Log.e("HTTP Error", "HTTP Exception: ${e.message()}")
            emit(ResultState.Error("HTTP error: ${e.code()} - ${e.message()}"))
        } catch (e: Exception) {
            // Handle any other exceptions
            Log.e("Error", "Exception: ${e.message}")
            Log.e("Error", "Exception: ${e.message}")
            emit(ResultState.Error(e.message ?: "An error occurred"))
        }
    }

    fun postRestockThreeRowData(request: PostRequest) = liveData {
        emit(ResultState.Loading) // Emit loading state before the network call
        try {
            val successResponse = apiService.postRestockThreeRowData(request) // This is CommonResponse
            emit(ResultState.Success(successResponse))
            Log.d("RestockSuccess", successResponse.toString())
        } catch (e: HttpException) {
            // Handle HTTP exceptions
            Log.e("HTTP Error", "HTTP Exception: ${e.message()}")
            emit(ResultState.Error("HTTP error: ${e.code()} - ${e.message()}"))
        } catch (e: Exception) {
            // Handle any other exceptions
            Log.e("Error", "Exception: ${e.message}")
            emit(ResultState.Error(e.message ?: "An error occurred"))
        }
    }

    fun postRestockOneRowData(request: PostOneRowRequest) = liveData {
        emit(ResultState.Loading) // Emit loading state before the network call
        try {
            val successResponse = apiService.postRestockOneRowData(request) // This is CommonResponse
            emit(ResultState.Success(successResponse))
            Log.d("RestockSuccess", successResponse.toString())
        } catch (e: HttpException) {
            // Handle HTTP exceptions
            Log.e("HTTP Error", "HTTP Exception: ${e.message()}")
            emit(ResultState.Error("HTTP error: ${e.code()} - ${e.message()}"))
        } catch (e: Exception) {
            // Handle any other exceptions
            Log.e("Error", "Exception: ${e.message}")
            emit(ResultState.Error(e.message ?: "An error occurred"))
        }
    }
    companion object {
        @Volatile
        private var instance: PostRestockRepository? = null

        fun getInstance(apiService: ApiService): PostRestockRepository =
            instance ?: synchronized(this) {
                instance ?: PostRestockRepository(apiService).also { instance = it }
            }
    }
}