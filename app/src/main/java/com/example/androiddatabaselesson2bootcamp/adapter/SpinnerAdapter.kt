package com.example.androiddatabaselesson2bootcamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.androiddatabaselesson2bootcamp.databinding.ItemSpinnerBinding

class SpinnerAdapter(var spinnerList: ArrayList<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return spinnerList.size
    }

    override fun getItem(position: Int): String {
        return spinnerList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        binding.itemTv.text = spinnerList[position]

        var itemView: View
        if (convertView == null) {
            itemView = binding.root
        } else {
            itemView = convertView
        }

        return itemView
    }

}