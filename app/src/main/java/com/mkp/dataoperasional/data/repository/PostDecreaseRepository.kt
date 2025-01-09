package com.mkp.dataoperasional.data.repository


import PostOneRowRequest
import PostRequest
import android.util.Log
import androidx.lifecycle.liveData
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.data.retrofit.ApiService
import retrofit2.HttpException

class PostDecreaseRepository(private val apiService: ApiService) {
    fun postDecreaseClothesWearPack(request: PostRequest) = liveData {
        emit(ResultState.Loading) // Emit loading state before the network call
        try {
            val successResponse = apiService.postDecreaseClothesWearPack(request) // This is CommonResponse
            emit(ResultState.Success(successResponse))
            Log.d("DecreaseSuccess", successResponse.toString())
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

    fun postDecreasePantsWearPack(request: PostRequest) = liveData {
        emit(ResultState.Loading) // Emit loading state before the network call
        try {
            val successResponse = apiService.postDecreasePantsWearPack(request) // This is CommonResponse
            emit(ResultState.Success(successResponse))
            Log.d("DecreaseSuccess", successResponse.toString())
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

    fun postDecreaseThreeRowData(request: PostRequest) = liveData {
        emit(ResultState.Loading) // Emit loading state before the network call
        try {
            val successResponse = apiService.postDecreaseThreeRowData(request) // This is CommonResponse
            emit(ResultState.Success(successResponse))
            Log.d("DecreaseSuccess", successResponse.toString())
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

    fun postDecreaseOneRowData(request: PostOneRowRequest) = liveData {
        emit(ResultState.Loading) // Emit loading state before the network call
        try {
            val successResponse = apiService.postDecreaseOneRowData(request) // This is CommonResponse
            emit(ResultState.Success(successResponse))
            Log.d("DecreaseSuccess", successResponse.toString())
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
        private var instance: PostDecreaseRepository? = null

        fun getInstance(apiService: ApiService): PostDecreaseRepository =
            instance ?: synchronized(this) {
                instance ?: PostDecreaseRepository(apiService).also { instance = it }
            }
    }
}