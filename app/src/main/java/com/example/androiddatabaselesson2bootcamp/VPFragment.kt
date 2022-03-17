package com.example.androiddatabaselesson2bootcamp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import com.example.androiddatabaselesson2bootcamp.adapter.RecyclerViewAdapter
import com.example.androiddatabaselesson2bootcamp.databinding.FragmentVPBinding
import com.example.androiddatabaselesson2bootcamp.db.BootcampDb
import com.example.androiddatabaselesson2bootcamp.models.BSWType
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.androiddatabaselesson2bootcamp.adapter.SpinnerAdapter
import com.example.androiddatabaselesson2bootcamp.databinding.CustomDialogAdditionBinding


private const val ARG_PARAM1 = "list"


class VPFragment : Fragment() {
    private var type: String? = null
    var r: String? = null
    lateinit var list: ArrayList<BSWType>
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var binding: FragmentVPBinding
    lateinit var bootcampDb: BootcampDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(ARG_PARAM1)
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bootcampDb = BootcampDb(requireContext())
        binding = FragmentVPBinding.inflate(inflater, container, false)
        list = loadList(type!!)
        recyclerViewAdapter = RecyclerViewAdapter(list)
        binding.fragmentRV.adapter = recyclerViewAdapter

        return binding.root
    }

    private fun loadList(type: String): ArrayList<BSWType> {
        var list = ArrayList<BSWType>()
        when (type) {
            resources.getString(R.string.spinnerBasic) -> {
                list = bootcampDb.getAllBasic()
            }
            resources.getString(R.string.spinnerWorld) -> {
                list = bootcampDb.getAllWorld()
            }
            resources.getString(R.string.spinnerSocial) -> {
                list = bootcampDb.getAllSocial()
            }
        }
        return list
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            VPFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }


    override fun onResume() {
        super.onResume()
        bootcampDb = BootcampDb(requireContext())
        list = loadList(type!!)
        recyclerViewAdapter = RecyclerViewAdapter(list)
        binding.fragmentRV.adapter = recyclerViewAdapter

        recyclerViewAdapter.setOnMyItemClickListener(object :
            RecyclerViewAdapter.OnMyItemClickListener {
            override fun onClick(bswType: BSWType) {
                val bundleOf = bundleOf("ID" to bswType.id, "type" to type)
                findNavController().navigate(R.id.aboutFragment, bundleOf)
            }

        })
        recyclerViewAdapter.setOnMyItemClickListenerForPopupMenu(object :
            RecyclerViewAdapter.OnMyItemClickListenerForPopupMenu {
            override fun onItemClick(bswType: BSWType, position: Int, imageView: ImageView) {
                val popupMenu = PopupMenu(requireContext(), imageView)
                popupMenu.inflate(R.menu.popup_menu)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    popupMenu.setForceShowIcon(true)
                }
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item?.itemId) {
                        R.id.edit -> {
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
                                var itemSpinner =
                                    spinnerList[itemView.spinner.selectedItemPosition]
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
                                                list[position] = bswType
                                                recyclerViewAdapter.notifyItemChanged(position)
                                            }
                                            resources.getString(R.string.spinnerWorld) -> {
                                                bootcampDb.updateWorld(bswType)
                                                list[position] = bswType
                                                recyclerViewAdapter.notifyItemChanged(position)
                                            }
                                            resources.getString(R.string.spinnerSocial) -> {
                                                bootcampDb.updateSocial(bswType)
                                                list[position] = bswType
                                                recyclerViewAdapter.notifyItemChanged(position)
                                            }
                                        }
                                    } else {
                                        when (beginSelectedSpinner) {
                                            resources.getString(R.string.spinnerBasic) -> {
                                                bootcampDb.deleteBasic(bswType)
                                                list.remove(bswType)
                                                recyclerViewAdapter.notifyItemRemoved(list.size)
                                                recyclerViewAdapter.notifyItemRangeRemoved(
                                                    position,
                                                    list.size
                                                )

                                            }
                                            resources.getString(R.string.spinnerWorld) -> {
                                                bootcampDb.deleteWorld(bswType)
                                                list.remove(bswType)
                                                recyclerViewAdapter.notifyItemRemoved(list.size)
                                                recyclerViewAdapter.notifyItemRangeRemoved(
                                                    position,
                                                    list.size
                                                )
                                            }
                                            resources.getString(R.string.spinnerSocial) -> {
                                                bootcampDb.deleteSocial(bswType)
                                                list.remove(bswType)
                                                recyclerViewAdapter.notifyItemRemoved(list.size)
                                                recyclerViewAdapter.notifyItemRangeRemoved(
                                                    position,
                                                    list.size
                                                )
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
                                    }

                                    dialog.dismiss()
                                } else {

                                }


                            }
                            dialog.show()
                        }
                        R.id.delete -> {
                            val deleteDialog = AlertDialog.Builder(requireContext())
                            deleteDialog.setMessage("Do you want to delete this data ?")
                            deleteDialog.setCancelable(true)
                            deleteDialog.create()

                            deleteDialog.setNegativeButton("No",
                                object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface?, which: Int) {
                                        dialog?.dismiss()
                                    }

                                })
                            deleteDialog.setPositiveButton("Yes",
                                object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface?, which: Int) {
                                        when (type) {
                                            resources.getString(R.string.spinnerBasic) -> {
                                                bootcampDb.deleteBasic(bswType)
                                                list.remove(bswType)
                                                recyclerViewAdapter.notifyItemRemoved(list.size)
                                                recyclerViewAdapter.notifyItemRangeRemoved(
                                                    position,
                                                    list.size
                                                )

                                            }
                                            resources.getString(R.string.spinnerWorld) -> {
                                                bootcampDb.deleteWorld(bswType)
                                                list.remove(bswType)
                                                recyclerViewAdapter.notifyItemRemoved(list.size)
                                                recyclerViewAdapter.notifyItemRangeRemoved(
                                                    position,
                                                    list.size
                                                )
                                            }
                                            resources.getString(R.string.spinnerSocial) -> {
                                                bootcampDb.deleteSocial(bswType)
                                                list.remove(bswType)
                                                recyclerViewAdapter.notifyItemRemoved(list.size)
                                                recyclerViewAdapter.notifyItemRangeRemoved(
                                                    position,
                                                    list.size
                                                )
                                            }
                                        }
                                    }

                                })

                            deleteDialog.show()

                        }
                    }
                    true
                }
                popupMenu.show()
            }

        })



    }


}