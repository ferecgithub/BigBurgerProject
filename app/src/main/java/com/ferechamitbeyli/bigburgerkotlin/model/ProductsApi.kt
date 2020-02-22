package com.ferechamitbeyli.bigburgerkotlin.model

import io.reactivex.Single
import retrofit2.http.GET

interface ProductsApi {
    @GET("dump/mobiletest1.json")
    fun getProducts(): Single<List<Product>>

}