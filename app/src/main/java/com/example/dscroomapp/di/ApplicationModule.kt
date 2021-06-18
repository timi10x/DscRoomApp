package com.example.dscroomapp.di

import android.content.Context
import com.example.dscroomapp.BuildConfig
import com.example.dscroomapp.local.AppDatabase
import com.example.dscroomapp.local.UserDao
import com.example.dscroomapp.remote.UserItemRemoteDataSource
import com.example.dscroomapp.remote.UserService
import com.example.dscroomapp.respository.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://timmycoder-crud.herokuapp.com/")
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun createClient() : OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG){
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    fun provideGson() : Gson = GsonBuilder().create()

    @Provides
    fun provideService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(userService: UserService) = UserItemRemoteDataSource(userService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideDao(db: AppDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: UserItemRemoteDataSource, localDataSource: UserDao) =
        UserRepository(remoteDataSource, localDataSource)
}