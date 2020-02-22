package com.ferechamitbeyli.bigburgerkotlin.model

import com.ferechamitbeyli.bigburgerkotlin.dependencyInjection.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class ProductsService {
    @Inject
    lateinit var api: ProductsApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getProducts(): Single<List<Product>> {
        return api.getProducts()
    }
}