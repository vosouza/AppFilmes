package com.vosouza.appfilmes.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_SECONDS = 15L

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setStrictness(Strictness.LENIENT)
        .create()

    @Provides
    fun provideConverterFactory(gson: Gson) : String =""

}