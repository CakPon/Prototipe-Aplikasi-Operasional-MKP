package com.mkp.dataoperasional.ui.decrease

import PostOneRowRequest
import PostRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.data.repository.GetDataRepository
import com.mkp.dataoperasional.data.repository.PostDecreaseRepository

class DecreaseViewModel(
    private val getDataRepository: GetDataRepository,
    private val postDecreaseRepository: PostDecreaseRepository
) : ViewModel() {

    fun getData() = liveData {
        emit(ResultState.Loading)
        emitSource(getDataRepository.getData())
    }

    fun postDecreaseClothesWearPack(request: PostRequest) = liveData {
        emit(ResultState.Loading)
        emitSource(postDecreaseRepository.postDecreaseClothesWearPack(request))
    }

    fun postDecreasePantsWearPack(request: PostRequest) = liveData {
        emit(ResultState.Loading)
        emitSource(postDecreaseRepository.postDecreasePantsWearPack(request))
    }

    fun postDecreaseThreeRowData(request: PostRequest) = liveData {
        emit(ResultState.Loading)
        emitSource(postDecreaseRepository.postDecreaseThreeRowData(request))
    }
    fun postDecreaseOneRowData(request: PostOneRowRequest) = liveData {
        emit(ResultState.Loading)
        emitSource(postDecreaseRepository.postDecreaseOneRowData(request))
    }
}