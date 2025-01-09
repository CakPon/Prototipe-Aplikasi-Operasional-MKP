package com.mkp.dataoperasional.data.retrofit

import PostFormRequest
import PostOneRowRequest
import PostRequest
import PostResponse
import retrofit2.http.Body
import retrofit2.http.POST
import com.mkp.dataoperasional.data.response.WearPackResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("exec?action=getData")
    suspend fun getData(): WearPackResponse

    @POST("exec")
    suspend fun postRestockClothesWearPack(
        @Body request: PostRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<PostResponse>

    @POST("exec")
    suspend fun postRestockPantsWearPack(
        @Body request: PostRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<PostResponse>

    @POST("exec")
    suspend fun postRestockThreeRowData(
        @Body request: PostRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<PostResponse>

    @POST("exec")
    suspend fun postRestockOneRowData(
        @Body request: PostOneRowRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<PostResponse>

    @POST("exec")
    suspend fun postDecreaseClothesWearPack(
        @Body request: PostRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<PostResponse>

    @POST("exec")
    suspend fun postDecreasePantsWearPack(
        @Body request: PostRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<PostResponse>

    @POST("exec")
    suspend fun postDecreaseThreeRowData(
        @Body request: PostRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<PostResponse>

    @POST("exec")
    suspend fun postDecreaseOneRowData(
        @Body request: PostOneRowRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<PostResponse>

    @POST("exec")
    suspend fun postForm(
        @Body request: PostFormRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<PostResponse>

}
