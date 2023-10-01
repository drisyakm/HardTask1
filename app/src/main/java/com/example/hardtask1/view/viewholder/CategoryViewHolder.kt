package com.example.hardtask1.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.hardtask1.databinding.ListItemBinding
import com.example.hardtask1.model.Category
import com.example.hardtask1.view.ItemSelectionListener
import java.lang.ref.WeakReference

class CategoryViewHolder(private var binding: ListItemBinding,val listener: WeakReference<ItemSelectionListener<Category>>,val lang:String): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.language = lang
        itemView.setOnClickListener {
            listener.get()?.onItemSelected(binding.category,layoutPosition)
        }
    }
    fun bind(category: Category){
        binding.category = category
    }
}