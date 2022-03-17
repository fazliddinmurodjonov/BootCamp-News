package com.example.androiddatabaselesson2bootcamp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androiddatabaselesson2bootcamp.VPFragment
import com.example.androiddatabaselesson2bootcamp.models.Category

class ViewPagerAdapter(var list: ArrayList<String>, fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return  VPFragment.newInstance(list[position])
    }

}