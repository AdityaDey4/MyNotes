package com.example.mynotes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyNotes")
data class Notes (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val text: String
)