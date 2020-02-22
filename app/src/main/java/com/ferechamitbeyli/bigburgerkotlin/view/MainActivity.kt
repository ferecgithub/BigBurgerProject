package com.ferechamitbeyli.bigburgerkotlin.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ferechamitbeyli.bigburgerkotlin.R
import com.ferechamitbeyli.bigburgerkotlin.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    private val productsAdapter = ProductListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()


        productsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }

        observeViewModel()

        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productsAdapter.filter.filter(newText)
                return false
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.cart_menu -> {
                intent = Intent(this, CheckoutActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.share -> {
                val shareIntent = Intent()
                val shareText = getString(R.string.share_text)
                val shareSubject = getString(R.string.share_subject)
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)))
            }
            R.id.exit -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeViewModel() {
        viewModel.products.observe(this, Observer { products ->
            products?.let {
                productsList.visibility = View.VISIBLE
                productsAdapter.updateProducts(it)
            }
        })

        viewModel.productLoadError.observe(this, Observer { isError ->
            isError?.let { list_error.visibility = if (it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_error.visibility = View.GONE
                    productsList.visibility = View.GONE
                }
            }
        })
    }
}


