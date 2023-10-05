package com.mohit.demo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mohit.demo.R
import com.mohit.demo.model.Product

class ProductRvAdapter(
    private val onCheckBoxClick: (() -> Unit)? = null,
    val list: ArrayList<Product>
) :
    RecyclerView.Adapter<ProductRvAdapter.ProductViewHolder>() {

    val filterList = ArrayList<Product>()

    fun filter(input: String) {
        filterList.clear()
        for (item in list) {
            if (item.name.equals(input)) {
                filterList.add(item)
            }
        }
        notifyDataSetChanged()

    }

    fun restoreData() {
        filterList.clear()
        filterList.addAll(list)
        notifyDataSetChanged()
    }


    class ProductViewHolder(itemView: View) : ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.item_cb);
        val title: TextView = itemView.findViewById(R.id.item_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item, parent, false)
        return ProductViewHolder(view)

    }

    override fun getItemCount(): Int {
        return filterList.size

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.title.text = filterList.get(position).name;
        holder.checkBox.isChecked = filterList.get(position).isSelected
        holder.checkBox.setOnClickListener {
            val index = list.find {
                it.id == filterList.get(position).id
            }
            index?.isSelected = holder.checkBox.isChecked
            onCheckBoxClick?.invoke()
        }


    }
}