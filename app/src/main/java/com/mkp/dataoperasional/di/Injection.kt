//package com.aloysius.dataoperasional.di
//
//import android.content.Context
//import kotlinx.coroutines.runBlocking
//
//object Injection {
//    fun provideWearPackRepository(context: Context) {
//
//        val apiService = ApiConfig.getApiService()
//        val registerRepository = RegisterRepository.getInstance(apiService)
//        val loginRepository = LoginRepository.getInstance(apiService)
//        val newsRepository = NewsRepository.getInstance(apiService)
//        val forgotPasswordRepository = ForgotPasswordRepository.getInstance(apiService)
//        val resetPasswordRepository = ResetPasswordRepository.getInstance(apiService)
//        return UserRepository.getInstance(userPreference, registerRepository, loginRepository,newsRepository, forgotPasswordRepository,resetPasswordRepository )
//    }
//
//    fun provideUserPreference(context: Context): UserPreference {
//        return UserPreference.getInstance(context.dataStore)
//    }
//
//
//    fun provideDrugRepository(context: Context): DrugRepository {
//        val userPreference = UserPreference.getInstance(context.dataStore)
//        val user = runBlocking { userPreference.getSession().first() }
//        TokenManager.setToken(user.token)
//        val apiService = ApiConfig.getApiService()
//        return DrugRepository.getInstance(apiService)
//    }
//    fun provideHistoryRepository(context: Context): HistoryRepository {
//        val userPreference = UserPreference.getInstance(context.dataStore)
//        val user = runBlocking { userPreference.getSession().first() }
//        TokenManager.setToken(user.token)
//        val apiService = ApiConfig.getApiService()
//        return HistoryRepository.getInstance(apiService)
//    }
//
//}
//
