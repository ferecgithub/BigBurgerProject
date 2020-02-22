package com.ferechamitbeyli.bigburgerkotlin.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ferechamitbeyli.bigburgerkotlin.R
import com.ferechamitbeyli.bigburgerkotlin.model.Product
import com.ferechamitbeyli.bigburgerkotlin.util.getProgressDrawable
import com.ferechamitbeyli.bigburgerkotlin.util.loadImage
import kotlinx.android.synthetic.main.cart_product.view.*
import java.util.*

/**
 * This class controls RecyclerView of the Cart (Checkout Activity)
 */
class CartRecyclerViewAdapter(private var productsInCart: ArrayList<Product>) :
    RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>() {

    override fun getItemCount() = productsInCart.size

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = CartViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cart_product, parent, false)
    )

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val productInCart = productsInCart[position]
        holder.setData(productInCart)
    }


    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.img_product
        private val productName = view.txt_product_name
        private val productPrice = view.txt_price
        private val totalAmount = view.total_amount
        private val totalPayment = view.total_payment
        private var removeButton = view.removeButton

        private val progressDrawable = getProgressDrawable(view.context)

        fun setData(product: Product) {
            productName.text = product.title
            val correctPrice = product.price?.toDouble()?.div(100)
            productPrice.text = correctPrice.toString()
            imageView.loadImage(product.image, progressDrawable)
            totalAmount.text = product.amountInCart.toString()
            val payment = correctPrice?.times(product.amountInCart)
            val formattedString = "%.2f".format(payment)
            totalPayment.text = formattedString

            removeButton.setOnClickListener {
                Product.boughtItems.remove(product)
                notifyDataSetChanged()
            }
        }
    }
}