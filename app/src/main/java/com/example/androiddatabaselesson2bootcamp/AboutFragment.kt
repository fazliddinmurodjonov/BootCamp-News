package com.example.androiddatabaselesson2bootcamp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.androiddatabaselesson2bootcamp.adapter.SpinnerAdapter
import com.example.androiddatabaselesson2bootcamp.databinding.CustomDialogAdditionBinding
import com.example.androiddatabaselesson2bootcamp.databinding.FragmentAboutBinding
import com.example.androiddatabaselesson2bootcamp.db.BootcampDb
import com.example.androiddatabaselesson2bootcamp.models.BSWType

class AboutFragment : Fragment() {
    lateinit var bootcampDb: BootcampDb
    lateinit var binding: FragmentAboutBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        val id = arguments?.getInt("ID")
        var type = arguments?.getString("type")
        bootcampDb = BootcampDb(requireContext())
        var bswType = BSWType()
        when (type) {
            resources.getString(R.string.spinnerBasic) -> {

                val basicById = bootcampDb.getBasicById(id!!)
                bswType = basicById
            }
            resources.getString(R.string.spinnerWorld) -> {
                val worldById = bootcampDb.getWorldById(id!!)
                bswType = worldById

            }
            resources.getString(R.string.spinnerSocial) -> {
                val socialById = bootcampDb.getSocialById(id!!)
                bswType = socialById
            }
        }
        binding.titleTv.text = bswType.title
        binding.textTv.text = bswType.text

        binding.editButton.setOnClickListener {
            val dialog = Dialog(requireContext())
            val itemView =
                CustomDialogAdditionBinding.inflate(
                    LayoutInflater.from(requireContext()),
                    null,
                    false
                )
            var spinnerList = ArrayList<String>()
            spinnerList.add(resources.getString(R.string.spinnerBasic))
            spinnerList.add(resources.getString(R.string.spinnerWorld))
            spinnerList.add(resources.getString(R.string.spinnerSocial))

            var spinnerAdapter: SpinnerAdapter = SpinnerAdapter(spinnerList)
            itemView.spinner.adapter = spinnerAdapter

            itemView.titleTv.setText(bswType.title.toString())
            itemView.textTv.setText(bswType.text.toString())
            var beginSelectedSpinner = ""
            when (type) {
                spinnerList[0] -> {
                    itemView.spinner.setSelection(0)
                    beginSelectedSpinner = spinnerList[0]
                }

                spinnerList[1] -> {
                    itemView.spinner.setSelection(1)
                    beginSelectedSpinner = spinnerList[1]

                }

                spinnerList[2] -> {
                    itemView.spinner.setSelection(2)
                    beginSelectedSpinner = spinnerList[2]

                }
            }
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(itemView.root)

            itemView.cancelTv.setOnClickListener {
                dialog.dismiss()
            }
            itemView.saveTv.setOnClickListener {
                var itemSpinner = spinnerList[itemView.spinner.selectedItemPosition]
                type = itemSpinner
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

                    bswType.title = itemView.titleTv.text.toString()
                    bswType.text = itemView.textTv.text.toString()
                    if (beginSelectedSpinner == itemSpinner) {
                        when (beginSelectedSpinner) {
                            resources.getString(R.string.spinnerBasic) -> {
                                bootcampDb.updateBasic(bswType)

                            }
                            resources.getString(R.string.spinnerWorld) -> {
                                bootcampDb.updateWorld(bswType)

                            }
                            resources.getString(R.string.spinnerSocial) -> {
                                bootcampDb.updateSocial(bswType)

                            }
                        }
                        binding.titleTv.text = bswType.title
                        binding.textTv.text = bswType.text
                    } else {
                        when (beginSelectedSpinner) {
                            resources.getString(R.string.spinnerBasic) -> {
                                bootcampDb.deleteBasic(bswType)

                            }
                            resources.getString(R.string.spinnerWorld) -> {
                                bootcampDb.deleteWorld(bswType)


                            }
                            resources.getString(R.string.spinnerSocial) -> {
                                bootcampDb.deleteSocial(bswType)
                            }
                        }

                        when (itemSpinner) {
                            resources.getString(R.string.spinnerBasic) -> {
                                bootcampDb.insertBasic(bswType)

                            }
                            resources.getString(R.string.spinnerWorld) -> {
                                bootcampDb.insertWorld(bswType)

                            }
                            resources.getString(R.string.spinnerSocial) -> {
                                bootcampDb.insertSocial(bswType)

                            }
                        }
                        binding.titleTv.text = bswType.title
                        binding.textTv.text = bswType.text
                    }
                    type = itemSpinner

                    dialog.dismiss()
                } else {

                }


            }
            dialog.show()
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()

        }
        return binding.root
    }


}