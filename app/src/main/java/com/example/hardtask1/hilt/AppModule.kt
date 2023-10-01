package com.example.hardtask1.hilt

import android.content.Context
import android.content.SharedPreferences
import com.example.hardtask1.retrofit.ApiClient
import com.example.hardtask1.retrofit.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    internal fun provideApiInterface(): ApiInterface {
        return ApiClient.buildService(ApiInterface::class.java)
    }


    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("preferences_task1", Context.MODE_PRIVATE)
    }

}