package com.ferechamitbeyli.bigburgerkotlin.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.ferechamitbeyli.bigburgerkotlin.R
import com.ferechamitbeyli.bigburgerkotlin.model.Product
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

    private val cartAdapter = CartRecyclerViewAdapter(Product.boughtItems)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)


        recycler_cart.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
            hasFixedSize()
        }

        btn_place_order.setOnClickListener {
            var totalAmount = 0.0

            for (i in Product.boughtItems) {
                totalAmount += i.price?.toDouble()?.div(100)?.times(i.amountInCart)!!
            }
            val formattedString = "%.2f".format(totalAmount)
            if (totalAmount == 0.0) {
                Toast.makeText(this, getString(R.string.empty_basket_text), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Your order is on the way!\nTotal price is $formattedString  ₺.", Toast.LENGTH_LONG).show()
            }
            Product.boughtItems.clear()
            recycler_cart.removeAllViews()

        }

        btn_back_to_menu.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }



    }

}
