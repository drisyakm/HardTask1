package com.example.hardtask1.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hardtask1.R
import com.example.hardtask1.databinding.ActivitySubCategoryBinding
import com.example.hardtask1.model.Category
import com.example.hardtask1.view.adapter.CategoryRecyclerAdapter
import com.example.hardtask1.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class SubCategoryActivity : AppCompatActivity(),ItemSelectionListener<Category> {
    lateinit var mainViewModel:  MainViewModel
    private var categoryAdapter: CategoryRecyclerAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding:ActivitySubCategoryBinding = DataBindingUtil.setContentView(this,R.layout.activity_sub_category)

        val selectedId = intent.getIntExtra("selectedCatId",-1)
        val selectedCatName = intent.getStringExtra("selectedCatName")?:""

        binding.textTitle.text = selectedCatName
        binding.imgBack.setOnClickListener { finish() }

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        categoryAdapter = CategoryRecyclerAdapter(mutableListOf(), WeakReference(this), mainViewModel.getLanguage())
        binding.rvCategories.adapter = categoryAdapter
        mainViewModel.catLiveData.observe(this) {
            binding.loading = false
            if (it != null) {
                categoryAdapter?.addItems(it)

            } else {
                //show error
            }
        }

        binding.loading = true
        mainViewModel.getSubCategories(selectedId)

    }

    override fun onItemSelected(item: Category?, position: Int) {
        if(item?.IsLastChild == true){
            Toast.makeText(this, R.string.last_child_msg, Toast.LENGTH_LONG).show()
        }else{
            val intent = Intent(this, SubCategoryActivity::class.java)
            intent.putExtra("selectedCatId",item?.categoryId)
            intent.putExtra("selectedCatName",if (mainViewModel.getLanguage()=="en") item?.NameEn else item?.NameAr)
            startActivity(intent)
        }

    }
}