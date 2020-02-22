package com.ferechamitbeyli.bigburgerkotlin.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Product(

    @SerializedName("ref")
    var ref: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("description")
    var desc: String?,
    @SerializedName("thumbnail")
    var image: String?,
    @SerializedName("price")
    var price: String?,
    var amountInCart: Int

) {

    companion object {
        var boughtItems = ArrayList<Product>()

        /**
         * The function below adjusts the list as it deletes products with same ref until there is only one with
         * the ref and sums deleted products in amountInCart of remained product
         *
         */
        fun correctTheList(boughtItemList: ArrayList<Product>): ArrayList<Product> {

            try {
                for (i in boughtItemList) {
                    for (j in boughtItemList) {
                        if(boughtItemList.size <= 1) {
                            return boughtItemList
                        }
                        if(i.ref == j.ref && i !== j) {
                            i.amountInCart += j.amountInCart
                            boughtItemList.remove(j)
                        }
                    }
                }
            } catch (e: Exception) {
                println(e.message)
            }

            return boughtItemList
        }
    }

}