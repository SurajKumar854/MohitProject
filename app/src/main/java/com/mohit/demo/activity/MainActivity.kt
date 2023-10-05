package com.mohit.demo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.mohit.demo.R
import com.mohit.demo.adapter.ProductRvAdapter
import com.mohit.demo.model.Product
import java.util.UUID

class MainActivity : AppCompatActivity() {

    lateinit var productRv: RecyclerView
    lateinit var productAdapter: ProductRvAdapter;
    var productList: ArrayList<Product> = ArrayList()
    lateinit var selectAllCheckBox: CheckBox;
    lateinit var searchEdt: EditText;
    lateinit var selectedItemsTv: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        listeners()


    }

    private fun listeners() {
        selectAllCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                checkAll()
            } else {
                unCheckAll()
            }
            displayName()
        }



        searchEdt.doAfterTextChanged { newText ->
            if (newText.toString().isNotBlank()) {
                productAdapter.filter(newText.toString())
            } else {
                productAdapter.restoreData()
                displayName()
            }


        }

    }

    fun displayName() {

        if (selectAllCheckBox.isChecked){
            selectedItemsTv.text = "Selected all"
        }else {
            val selectedProducts = productList.filter { it.isSelected }.joinToString(", ") { it.name }
            selectedItemsTv.text = selectedProducts

        }

    }

    private fun unCheckAll() {
        productList.forEach {
            if (it.isSelected) {
                it.isSelected = false;
            }
        }
        productAdapter.notifyDataSetChanged()
    }

    private fun checkAll() {
        productList.forEach {
            if (!it.isSelected) {
                it.isSelected = true;
            }
        }

        productAdapter.notifyDataSetChanged()
    }

    private fun initView() {
        for (i in 1..100) {
            productList.add(Product(UUID.randomUUID().toString(), "item $i"))
        }
        selectAllCheckBox = findViewById(R.id.selectAllCB)
        productRv = findViewById(R.id.product_rv)
        productRv.layoutManager = LinearLayoutManager(this)
        productAdapter = ProductRvAdapter(onCheckBoxClick = {
            displayName()
        }, productList);
        productRv.adapter = productAdapter
        productAdapter.restoreData()
        searchEdt = findViewById(R.id.searchEdt);
        selectedItemsTv = findViewById(R.id.selectedItems)
    }
}