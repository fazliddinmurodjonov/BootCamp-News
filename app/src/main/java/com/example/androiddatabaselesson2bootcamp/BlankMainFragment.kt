package com.example.androiddatabaselesson2bootcamp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.androiddatabaselesson2bootcamp.databinding.FragmentBlankMainBinding

class BlankMainFragment : Fragment() {

    lateinit var binding: FragmentBlankMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlankMainBinding.inflate(inflater, container, false)
        binding.textView.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }
        return binding.root
    }


}