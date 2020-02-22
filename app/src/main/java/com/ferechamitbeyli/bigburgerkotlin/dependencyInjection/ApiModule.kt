package com.ferechamitbeyli.bigburgerkotlin.dependencyInjection

import com.ferechamitbeyli.bigburgerkotlin.model.ProductsApi
import com.ferechamitbeyli.bigburgerkotlin.model.ProductsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    private val BASE_URL = "http://legacy.vibuy.com"

    @Provides
    fun provideProductsApi(): ProductsApi {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ProductsApi::class.java)
    }

    @Provides
    fun provideProductsService(): ProductsService {
        return ProductsService()
    }
}