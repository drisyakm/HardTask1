package com.example.hardtask1.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hardtask1.databinding.ListItemBinding
import com.example.hardtask1.model.Category
import com.example.hardtask1.view.ItemSelectionListener
import com.example.hardtask1.view.viewholder.CategoryViewHolder

import java.lang.ref.WeakReference

class CategoryRecyclerAdapter(val items:MutableList<Category
        >,val listener: WeakReference<ItemSelectionListener<Category>>, val lang: String): RecyclerView.Adapter<CategoryViewHolder>(){

    private lateinit var binding:ListItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding,listener,lang)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
       val category = items[position]
        holder.bind(category)
    }

    fun addItems(items:List<Category>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}