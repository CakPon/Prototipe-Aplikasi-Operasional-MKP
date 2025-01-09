package com.mkp.dataoperasional.ui.form

import PostFormRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.data.repository.PostFormRepository

class FormViewModel(private val repository: PostFormRepository) : ViewModel() {
    fun uploadForm(request: PostFormRequest) = liveData {
        emit(ResultState.Loading)
        emitSource(repository.postForm(request))
    }
}
