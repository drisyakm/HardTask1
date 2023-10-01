package com.example.hardtask1.view

import android.content.Context
import android.content.Intent
import android.content.res.Configuration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast

import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hardtask1.R
import com.example.hardtask1.databinding.ActivityMainBinding
import com.example.hardtask1.model.Category
import com.example.hardtask1.util.LocaleHelper
import com.example.hardtask1.view.adapter.CategoryRecyclerAdapter
import com.example.hardtask1.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MainActivity : AppCompatActivity() ,ItemSelectionListener<Category>{
    lateinit var mainViewModel:  MainViewModel
    private var categoryAdapter:CategoryRecyclerAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.textLang.text = mainViewModel.getLanguage()
        binding.textLang.setOnClickListener {
            changeLanguage()
        }
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
        mainViewModel.getParentCategories()

    }

    fun changeLanguage(){
        val lng = mainViewModel.getLanguage()
        if(lng=="en"){
            mainViewModel.setLanguage("ar")
            LocaleHelper.setLocale(this,"ar")



        }else{
            mainViewModel.setLanguage("en")
            LocaleHelper.setLocale(this,"en")
        }

        recreate()
    }


    override fun attachBaseContext(newBase: Context?) {
        val lng = newBase?.getSharedPreferences("preferences_task1", Context.MODE_PRIVATE)?.getString("lang", "en")?:"en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase!!,lng))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        val lng = mainViewModel.getLanguage()
        LocaleHelper.onAttach(this,lng)
        super.onConfigurationChanged(newConfig)
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

@BindingAdapter("imgUrl")
fun ImageView.loadImage(url: String) {
    if(url.isEmpty()){
        this.setImageResource(R.drawable.cat_no_img)
    }
    else {
        Picasso.get().load(url)
            .placeholder(R.drawable.cat_no_img)
            .error(R.drawable.cat_no_img)
            .into(this)
    }
}

@BindingAdapter("android:visibility")
fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
