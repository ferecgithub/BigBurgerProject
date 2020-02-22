package com.ferechamitbeyli.bigburgerkotlin.dependencyInjection

import com.ferechamitbeyli.bigburgerkotlin.model.ProductsService
import com.ferechamitbeyli.bigburgerkotlin.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: ProductsService)

    fun inject(viewModel: ListViewModel)
}