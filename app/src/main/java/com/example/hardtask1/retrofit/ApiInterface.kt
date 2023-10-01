package com.example.hardtask1.retrofit


import com.example.hardtask1.model.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

   @GET("ParentCatgories")
   fun getParentCategories(): Call<List<Category>>

   @GET("SubCategories")
   fun getSubCategories(@Query("categoryId") catId:Int): Call<List<Category>>
}