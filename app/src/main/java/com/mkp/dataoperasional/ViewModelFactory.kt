package com.mkp.dataoperasional

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkp.dataoperasional.data.repository.PostRestockRepository
import com.mkp.dataoperasional.data.repository.GetDataRepository
import com.mkp.dataoperasional.data.repository.PostDecreaseRepository
import com.mkp.dataoperasional.data.repository.PostFormRepository
import com.mkp.dataoperasional.data.retrofit.ApiConfig
import com.mkp.dataoperasional.ui.check.GetDataViewModel
import com.mkp.dataoperasional.ui.decrease.DecreaseViewModel
import com.mkp.dataoperasional.ui.form.FormViewModel
import com.mkp.dataoperasional.ui.restock.RestockViewModel
import kotlinx.coroutines.runBlocking

class ViewModelFactory private constructor(
    private val getDataRepository: GetDataRepository,
    private val postRestockRepository: PostRestockRepository,
    private val postDecreaseRepository: PostDecreaseRepository,
    private val postFormRepository: PostFormRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GetDataViewModel::class.java) -> {
                GetDataViewModel(getDataRepository) as T
            }
            modelClass.isAssignableFrom(RestockViewModel::class.java) -> {
                RestockViewModel(getDataRepository, postRestockRepository) as T
            }
            modelClass.isAssignableFrom(DecreaseViewModel::class.java) -> {
                DecreaseViewModel(getDataRepository, postDecreaseRepository) as T
            }
            modelClass.isAssignableFrom(FormViewModel::class.java) -> {
                FormViewModel(postFormRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: runBlocking {
                    val apiService = ApiConfig.getApiService()
                    val wearPackRepository = GetDataRepository.getInstance(apiService)
                    val postRestockRepository = PostRestockRepository.getInstance(apiService)
                    val postDecreaseRepository = PostDecreaseRepository.getInstance(apiService)
                    val postFormRepository = PostFormRepository.getInstance(apiService)

                    ViewModelFactory(wearPackRepository, postRestockRepository, postDecreaseRepository, postFormRepository)
                }.also { INSTANCE = it }
            }
        }
    }
}
