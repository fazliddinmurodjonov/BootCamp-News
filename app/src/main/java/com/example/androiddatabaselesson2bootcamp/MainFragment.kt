package com.example.androiddatabaselesson2bootcamp

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddatabaselesson2bootcamp.adapter.SpinnerAdapter
import com.example.androiddatabaselesson2bootcamp.adapter.ViewPagerAdapter
import com.example.androiddatabaselesson2bootcamp.databinding.CustomDialogAdditionBinding
import com.example.androiddatabaselesson2bootcamp.databinding.FragmentMainBinding
import com.example.androiddatabaselesson2bootcamp.databinding.FragmentVPBinding
import com.example.androiddatabaselesson2bootcamp.db.BootcampDb
import com.example.androiddatabaselesson2bootcamp.models.*
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : Fragment() {
    lateinit var list: ArrayList<String>
    lateinit var binding: FragmentMainBinding
    lateinit var bootcampDb: BootcampDb
    var onResumeChecker = false
    var viewPagerAdapter: ViewPagerAdapter? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        bootcampDb = BootcampDb(requireContext())
        list = loadList()
        viewPagerAdapter = ViewPagerAdapter(list, requireActivity())
        binding.viewPager.adapter = viewPagerAdapter
        onResumeChecker = true
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = list[position]
        }.attach()
        binding.addText.setOnClickListener {
            val dialog = Dialog(requireContext())
            var itemView = CustomDialogAdditionBinding.inflate(
                LayoutInflater.from(requireContext()),
                null,
                false
            )

            dialog.setContentView(itemView.root)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.show()
            var spinnerAdapter: SpinnerAdapter = SpinnerAdapter(list)
            itemView.spinner.adapter = spinnerAdapter
            itemView.cancelTv.setOnClickListener {
                dialog.dismiss()

            }



            itemView.saveTv.setOnClickListener {
                var itemSpinner = list[itemView.spinner.selectedItemPosition]

                val title = itemView.titleTv.text.toString()
                val text = itemView.textTv.text.toString()

                var titleBol = false
                var textBol = false
                for (c in title) {
                    if (c != ' ') {
                        titleBol = true
                        break
                    }
                }
                for (c in text) {
                    if (c != ' ') {
                        textBol = true
                        break
                    }
                }
                if (title != " " && text != " " && titleBol && textBol) {

                    when (itemSpinner) {
                        list[0] -> {
                            var BSWType = BSWType(title, text)
                            bootcampDb.insertBasic(BSWType)
                        }
                        list[1] -> {
                            var BSWType = BSWType(title, text)
                            bootcampDb.insertWorld(BSWType)
                        }
                        list[2] -> {
                            var BSWType = BSWType(title, text)
                            bootcampDb.insertSocial(BSWType)
                        }
                    }
                    list = loadList()
                    val currentItem = binding.viewPager.currentItem
                    viewPagerAdapter = ViewPagerAdapter(list, requireActivity())
                    binding.viewPager.adapter = viewPagerAdapter
                    binding.viewPager.currentItem = currentItem
                    dialog.dismiss()
                } else {

                }
            }


        }
        setHasOptionsMenu(true)
        return binding.root
    }


    private fun loadList(): ArrayList<String> {
        var list = ArrayList<String>()
        list.add(resources.getString(R.string.spinnerBasic))
        list.add(resources.getString(R.string.spinnerWorld))
        list.add(resources.getString(R.string.spinnerSocial))
        return list
    }

    override fun onResume() {
        if (onResumeChecker) {
            viewPagerAdapter = ViewPagerAdapter(list, requireActivity())
            binding.viewPager.adapter = viewPagerAdapter
        }
        onResumeChecker = true
        super.onResume()
    }


    override fun onPause() {
        onResumeChecker = false
        super.onPause()
    }

}