package com.example.hardtask1.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hardtask1.model.Category
import com.example.hardtask1.retrofit.ApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiInterface: ApiInterface,private val sharedPreferences: SharedPreferences): ViewModel(){
    val catLiveData: MutableLiveData<List<Category>> by lazy {
        MutableLiveData<List<Category>>()
    }
    fun getParentCategories(){
        apiInterface.getParentCategories().enqueue(object : Callback<List<Category>>{
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if(response.isSuccessful && response.body()!=null){
                    catLiveData.postValue(response.body())
                }else{
                    catLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                catLiveData.postValue(null)
            }

        })
    }

    fun getSubCategories(catId:Int){
        apiInterface.getSubCategories(catId).enqueue(object : Callback<List<Category>>{
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if(response.isSuccessful && response.body()!=null){
                    catLiveData.postValue(response.body())
                }else{
                    catLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                catLiveData.postValue(null)
            }

        })
    }



    fun setLanguage(lang:String){
        sharedPreferences.edit().putString("lang", lang).apply()
    }
    fun getLanguage(): String {
        return sharedPreferences.getString("lang", "en")?:"en"
    }
}