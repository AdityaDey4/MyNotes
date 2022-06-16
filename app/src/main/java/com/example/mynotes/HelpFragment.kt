package com.example.mynotes

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class HelpFragment(val mainActivity: MainActivity) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_help, container, false)

        setHasOptionsMenu(true)

       requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
                ft?.replace(R.id.frameLayout,HomeFragment())
                mainActivity.bottomnev.selectedItemId = mainActivity.bottomnev.menu.get(0).itemId
                ft?.commit()
            }
        })
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }
}