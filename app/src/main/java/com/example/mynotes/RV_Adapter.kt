package com.example.mynotes

import android.annotation.SuppressLint
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class RV_Adapter(
    var homeContext: HomeFragment
): RecyclerView.Adapter<RV_Adapter.ViewHolder>() {
    private var note_List:List<Notes> = ArrayList<Notes>()//must assign or else error occurred
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.rv_title)
        val text: TextView = itemView.findViewById(R.id.rv_text)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (homeContext.isAdded && position != RecyclerView.NO_POSITION) {
                    homeContext.itemOnClick(position, note_List[adapterPosition])
                }
                true
            }
        }
    }
    class NotesDiffCallBack(var oldList: List<Notes>, var newList: List<Notes>):
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val n1: Notes = oldList[oldItemPosition]
            val n2: Notes = newList[newItemPosition]

            return n1.id==n2.id
                    && n1.text.equals(n2.text)
                    && n1.title.equals(n2.title)
        }
        @Nullable
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = note_List[position].title
        holder.text.text = note_List[position].text
    }

    override fun getItemCount(): Int {
        return note_List.size
    }
    fun getNoteAt(position: Int): Notes{
        return note_List[position]
    }


    fun setNotesList(list : List<Notes>){
        val diffCallBack = NotesDiffCallBack(note_List, list)
        val diffNotes = DiffUtil.calculateDiff(diffCallBack)
        this.note_List = list
        diffNotes.dispatchUpdatesTo(this)

    }

    interface MyClickListener{
        fun itemOnClick(position: Int, notes: Notes)
    }
}