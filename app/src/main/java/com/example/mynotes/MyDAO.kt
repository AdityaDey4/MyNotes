package com.example.mynotes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyDAO {

    @Insert
    suspend fun insertNote(notes: Notes)

    @Delete
    suspend fun deleteNote(notes: Notes)

    @Update
    suspend fun updateNote(notes: Notes)

    @Query("SELECT * FROM MyNotes ORDER BY id DESC")
    fun getNotes():LiveData<List<Notes>>

    @Query("DELETE FROM MyNotes")
    fun deleteAllNotes()
}