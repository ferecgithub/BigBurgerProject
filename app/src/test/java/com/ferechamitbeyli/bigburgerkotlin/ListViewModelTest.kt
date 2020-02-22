package com.ferechamitbeyli.bigburgerkotlin

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.ferechamitbeyli.bigburgerkotlin.model.Product
import com.ferechamitbeyli.bigburgerkotlin.model.ProductsService
import com.ferechamitbeyli.bigburgerkotlin.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var productsService: ProductsService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle: Single<List<Product>>? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getProductsSuccess() {
        val product = Product(1, "food name", "food description", "image url", "12", 1)
        val productsList = arrayListOf(product)

        testSingle = Single.just(productsList)

        `when`(productsService.getProducts()).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.products.value?.size)
        Assert.assertEquals(false, listViewModel.productLoadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getProductsFail() {
        testSingle = Single.error(Throwable())

        `when`(productsService.getProducts()).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(true, listViewModel.productLoadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Before
    fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }
}