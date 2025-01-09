package com.mkp.dataoperasional.ui.restock

import PostOneRowRequest
import PostRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.data.repository.PostRestockRepository
import com.mkp.dataoperasional.data.repository.GetDataRepository

class RestockViewModel(
    private val getDataRepository: GetDataRepository,
    private val postRestockRepository: PostRestockRepository
) : ViewModel() {

    fun getData() = liveData {
        emit(ResultState.Loading)
        emitSource(getDataRepository.getData())
    }

    fun postRestockClothesWearPack(request: PostRequest) = liveData {
        emit(ResultState.Loading)
        emitSource(postRestockRepository.postRestockClothesWearPack(request))
    }

    fun postRestockPantsWearPack(request: PostRequest) = liveData {
        emit(ResultState.Loading)
        emitSource(postRestockRepository.postRestockPantsWearPack(request))
    }

    fun postRestockThreeRowData(request: PostRequest) = liveData {
        emit(ResultState.Loading)
        emitSource(postRestockRepository.postRestockThreeRowData(request))
    }
    fun postRestockOneRowData(request: PostOneRowRequest) = liveData {
        emit(ResultState.Loading)
        emitSource(postRestockRepository.postRestockOneRowData(request))
    }
}