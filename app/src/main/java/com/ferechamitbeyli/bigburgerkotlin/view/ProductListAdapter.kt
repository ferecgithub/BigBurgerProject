package com.ferechamitbeyli.bigburgerkotlin.view

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.ferechamitbeyli.bigburgerkotlin.R
import com.ferechamitbeyli.bigburgerkotlin.model.Product
import com.ferechamitbeyli.bigburgerkotlin.util.getProgressDrawable
import com.ferechamitbeyli.bigburgerkotlin.util.loadImage
import kotlinx.android.synthetic.main.food_product.view.*

/**
 * This class controls RecyclerView of Product Catalog (Main Activity)
 */
class ProductListAdapter(private var products: ArrayList<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>(), Filterable {

    private var myFilter = FilterHelper(products, this)

    fun updateProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    /**
     * The function below manages the dialog for specifying the amount of item, after clicking buy button on a certain
     * item in Main Activity
     */
    fun showPurchaseDialog(context: Context, position: Int, itemList: ArrayList<Product>) {
        val myDialog = Dialog(context)
        myDialog.setContentView(R.layout.purchase_alert_dialog)
        val addToCartButton = myDialog.findViewById<Button>(R.id.add_to_cart_btn)
        val backToMenuButton = myDialog.findViewById<Button>(R.id.back_to_menu_btn)
        val itemAmountView = myDialog.findViewById<ElegantNumberButton>(R.id.txt_amount)

        itemAmountView.setOnValueChangeListener { _, _, newValue ->
            if (Product.boughtItems.isNotEmpty()) {
                itemList[position].amountInCart = newValue
            }
        }

        addToCartButton.setOnClickListener {
            /**
             * If there is an item with matched ref number just increment its amountInCart
             * Else (if there is not an item with matched ref), add that item to the list and assign its amountInCart
             */
            if (Product.boughtItems.contains(products[position])) {
                itemList[position].amountInCart += itemAmountView.number.toInt()
            } else {
                itemList[position].amountInCart = itemAmountView.number.toInt()
                Product.boughtItems.add(itemList[position])
            }
            Product.correctTheList(Product.boughtItems)
            myDialog.dismiss()
        }

        backToMenuButton.setOnClickListener {
            myDialog.dismiss()
        }

        notifyDataSetChanged()
        myDialog.show()
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = ProductViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.food_product, parent, false)
    )

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val instantProduct = products[position]
        holder.setData(instantProduct, position)
    }

    fun setFilter(arrayList: ArrayList<Product>) {
        products = arrayList
    }

    override fun getFilter(): Filter {
        return myFilter
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.imageView
        private val productName = view.title
        private val productDescription = view.description
        private val productPrice = view.price
        private val progressDrawable = getProgressDrawable(view.context)
        private val buyButton = view.buyButton
        private val context = view.context

        fun setData(product: Product, position: Int) {
            productName.text = product.title
            productDescription.text = product.desc
            imageView.loadImage(product.image, progressDrawable)
            val correctPrice = product.price?.toDouble()?.div(100)
            productPrice.text = correctPrice.toString()

            buyButton.setOnClickListener {
                showPurchaseDialog(context, position, products)
                println(Product.boughtItems)
            }

        }


    }
}

