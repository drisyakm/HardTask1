package com.example.hardtask1.model

import com.google.gson.annotations.SerializedName

data class Category(@SerializedName("ProductCategoryId") val categoryId:Int,val NameAr:String, val NameEn:String, val IsLastChild:Boolean, @SerializedName("Picture") val imgUrl:String, val SubProductCategoriesCount: Int)
