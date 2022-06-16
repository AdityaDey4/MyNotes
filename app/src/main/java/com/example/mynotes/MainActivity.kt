package com.example.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var bottomnev: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        bottomnev = findViewById(R.id.bottomnev)
        bottomnev.background = null
        bottomnev.menu.getItem(4).isEnabled = false
        bottomnev.menu.getItem(3).isEnabled = false

        bottomnev.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.my_home -> mainViewModel.changeFragmentID(1)
                R.id.about -> mainViewModel.changeFragmentID(2)
                R.id.help -> mainViewModel.changeFragmentID(3)
                else -> mainViewModel.changeFragmentID(1)
            }
        }
        mainViewModel.fragmentId.observe(this, Observer {
            mainViewModel.fragmentId.value?.let { loadFragment(it) }
        })
    }


    fun clickFAB(view: View) {
        Intent(this@MainActivity, CRU_Note::class.java).apply {
            putExtra("title", "Create Note")
            startActivityForResult(this, 1)
        }
    }

    private fun loadFragment(fragment_id: Int): Boolean{

        val fragment = when(fragment_id){
            2 -> AboutFragment(this)
            3 -> HelpFragment(this)
            else -> HomeFragment()
        }
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameLayout, fragment).isAddToBackStackAllowed
        fragmentTransaction.commit()

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == RESULT_OK){

            bottomnev.selectedItemId = bottomnev.menu.get(0).itemId
            mainViewModel.changeFragmentID(1)
            val title: String = data?.getStringExtra("title").toString()
            val text: String = data?.getStringExtra("text").toString()
            val homeFragment = HomeFragment()

            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("text", text)

            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            homeFragment.arguments = bundle
            fragmentTransaction.add(R.id.frameLayout, homeFragment).commit()
        }
    }

}

