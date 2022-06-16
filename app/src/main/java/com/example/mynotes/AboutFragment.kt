package com.example.mynotes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.fragment.app.FragmentTransaction

class AboutFragment(val mainActivity: MainActivity) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_about, container, false)

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