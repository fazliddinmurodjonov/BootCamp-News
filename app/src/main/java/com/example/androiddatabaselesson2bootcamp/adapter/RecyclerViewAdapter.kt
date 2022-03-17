package com.example.androiddatabaselesson2bootcamp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddatabaselesson2bootcamp.databinding.ItemRecyclerViewBinding
import com.example.androiddatabaselesson2bootcamp.models.BSWType

class RecyclerViewAdapter(var list: ArrayList<BSWType>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    lateinit var adapterListener: OnMyItemClickListenerForPopupMenu
    lateinit var itemListener: OnMyItemClickListener

    interface OnMyItemClickListener {
        fun onClick(bswType: BSWType)
    }

    fun setOnMyItemClickListener(listener: OnMyItemClickListener) {
        itemListener = listener
    }

    interface OnMyItemClickListenerForPopupMenu {
        fun onItemClick(bswType: BSWType, position: Int, imageView: ImageView)
    }


    fun setOnMyItemClickListenerForPopupMenu(listener: OnMyItemClickListenerForPopupMenu) {
        adapterListener = listener
    }

    inner class MyViewHolder(var binding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(bswType: BSWType, position: Int) {
            binding.titleTv.text = bswType.title
            binding.textRv.text = bswType.text
            binding.menuDotsRv.setOnClickListener {
                adapterListener.onItemClick(bswType, position, binding.menuDotsRv)
            }
            binding.root.setOnClickListener {
                itemListener.onClick(bswType)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val bswType = list[position]
        holder.onBind(bswType, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}