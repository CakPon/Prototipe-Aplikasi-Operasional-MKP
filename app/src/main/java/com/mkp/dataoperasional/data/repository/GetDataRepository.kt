package com.mkp.dataoperasional.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.data.retrofit.ApiService
import retrofit2.HttpException

class GetDataRepository private constructor(
    private val apiService: ApiService,
) {
    fun getData() = liveData {
        emit(ResultState.Loading) // Emit loading state before the network call
        try {
            val successResponse = apiService.getData() // This is GetWearPackResponse
            emit(ResultState.Success(successResponse))
            Log.d("JSON Response", successResponse.toString())
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
        private var instance: GetDataRepository? = null

        fun getInstance(apiService: ApiService): GetDataRepository =
            instance ?: synchronized(this) {
                instance ?: GetDataRepository(apiService).also { instance = it }
            }
    }
}
