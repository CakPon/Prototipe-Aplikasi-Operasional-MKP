package com.mkp.dataoperasional.ui.check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.data.repository.GetDataRepository

class GetDataViewModel(private val repository: GetDataRepository) : ViewModel() {

    fun getBajuWearPack() = liveData {
        emit(ResultState.Loading)
        emitSource(repository.getData())
    }
}