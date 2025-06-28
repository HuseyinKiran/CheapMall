package com.huseyinkiran.cheapmall.di

import com.huseyinkiran.cheapmall.core.Constants.BASE_URL
import com.huseyinkiran.cheapmall.data.api.ProductsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @[Singleton Provides]
    fun provideProductsApi(): ProductsApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ProductsApi::class.java)
    }

}