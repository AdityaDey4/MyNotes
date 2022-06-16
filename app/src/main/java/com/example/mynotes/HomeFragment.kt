package com.example.mynotes

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(), RV_Adapter.MyClickListener {
    lateinit var rv_Adapter: RV_Adapter
    var homeFragmentViewModel: HomeFragmentViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        homeFragmentViewModel = ViewModelProvider(requireActivity(), HomeFragmentViewModelFactory(requireActivity())).get(HomeFragmentViewModel::class.java)


        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(view?.context)
        rv_Adapter = view?.context?.let { RV_Adapter(this) }!!
        recyclerView?.adapter = rv_Adapter

        val bundle = arguments
        val title: String? = bundle?.getString("title")
        val text: String? = bundle?.getString("text")
        if(title?.isNotEmpty() == true || text?.isNotEmpty() == true){
            homeFragmentViewModel?.insert(Notes(0, title.toString(), text.toString()))
        }

        homeFragmentViewModel!!.get().observe(viewLifecycleOwner, Observer {
            rv_Adapter.setNotesList(it)
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                homeFragmentViewModel?.delete(rv_Adapter.getNoteAt(viewHolder.adapterPosition))
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        return view
    }


    override fun itemOnClick(position: Int, notes: Notes) {

        val noteId = notes.id
        val noteTitle = notes.title
        val noteText = notes.text
        Intent(requireActivity(), CRU_Note::class.java).apply {
            putExtra("title", "Edit Note")
            putExtra("noteText", noteText)
            putExtra("noteTitle", noteTitle)
            putExtra("noteId", noteId)
            startActivityForResult(this, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val title: String = data?.getStringExtra("title").toString()
        val text: String = data?.getStringExtra("text").toString()
        val id: Int = data?.getIntExtra("id", -1) ?:-1
        if(id != -1){
            homeFragmentViewModel?.update(Notes(id, title, text))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.clear()
        inflater.inflate(R.menu.delete_all, menu)

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.deleteAll){
            homeFragmentViewModel?.deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }
}