package com.ferechamitbeyli.bigburgerkotlin.view

import android.widget.Filter
import com.ferechamitbeyli.bigburgerkotlin.model.Product

/**
 * This class is responsible for filtering in the search bar (Main Activity)
 */
class FilterHelper(products: ArrayList<Product>, adapter: ProductListAdapter) : Filter() {

    private var currentList = products
    private var currentAdapter = adapter


    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val result = FilterResults()

        if (constraint != null && constraint.isNotEmpty()) {
            val searchedName = constraint.toString().toLowerCase()
            val matchedList = ArrayList<Product>()

            for (i in currentList) {
                val name = i.title?.toLowerCase()
                if (name!!.contains(searchedName)) {
                    matchedList.add(i)
                }
            }
            result.values = matchedList
            result.count = matchedList.size
        } else {
            result.values = currentList
            result.count = currentList.size
        }
        return result
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        currentAdapter.setFilter(results?.values as ArrayList<Product>)
        currentAdapter.notifyDataSetChanged()
    }

}