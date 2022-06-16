package com.example.mynotes

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteModelClass(context: Context) {
    var myDAO: MyDAO
    var allNotes: LiveData<List<Notes>>
    init {
        val database: MyDatabase = MyDatabase.getDataBaseObject(context)
        myDAO = database.getDao()
        allNotes = myDAO.getNotes()
    }

    fun insertNote(notes: Notes){
        GlobalScope.launch {
            myDAO.insertNote(notes)
        }
    }
    fun deleteNote(notes: Notes){
        GlobalScope.launch {
            myDAO.deleteNote(notes)
        }
    }
    fun updateNote(notes: Notes){
        GlobalScope.launch {
            myDAO.updateNote(notes)
        }
    }
    fun deleteAllNote(){
        GlobalScope.launch {
            myDAO.deleteAllNotes()
        }
    }
    fun getAllNote():LiveData<List<Notes>>{
        return allNotes
    }
}