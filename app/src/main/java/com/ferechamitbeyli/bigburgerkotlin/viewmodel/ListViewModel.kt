package com.ferechamitbeyli.bigburgerkotlin.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ferechamitbeyli.bigburgerkotlin.dependencyInjection.DaggerApiComponent
import com.ferechamitbeyli.bigburgerkotlin.model.Product
import com.ferechamitbeyli.bigburgerkotlin.model.ProductsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel : ViewModel() {

    @Inject
    lateinit var productsService: ProductsService

    init {
        DaggerApiComponent.create().inject(this)
    }



    private val disposable = CompositeDisposable()

    val products = MutableLiveData<List<Product>>()
    val productLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchProducts()
    }

    /**
     * Responsible for fetching information from remote connection
     */
    private fun fetchProducts() {
        loading.value = true
        disposable.add(
            productsService.getProducts().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(value: List<Product>) {
                        products.value = value
                        productLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        productLoadError.value = true
                        loading.value = false

                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}