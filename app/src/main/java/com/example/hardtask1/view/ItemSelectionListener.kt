package com.example.hardtask1.view

interface ItemSelectionListener<T> {
    fun onItemSelected(item:T?,position:Int)
}