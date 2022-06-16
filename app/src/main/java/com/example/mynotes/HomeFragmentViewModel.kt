package com.example.mynotes

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class HomeFragmentViewModel(context: Context): ViewModel() {
    var modelClass: NoteModelClass = NoteModelClass(context)
    var allNotes: LiveData<List<Notes>> = modelClass.getAllNote()

    fun insert(notes: Notes){
        modelClass.insertNote(notes)
    }
    fun delete(notes: Notes){
        modelClass.deleteNote(notes)

    }
    fun update(notes: Notes){
        modelClass.updateNote(notes)
    }
    fun deleteAll(){
        modelClass.deleteAllNote()
    }
    fun get():LiveData<List<Notes>>{
        return allNotes
    }
}
