package com.example.mynotes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Notes::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getDao(): MyDAO

    companion object {
        private var myDatabase: MyDatabase? = null

        fun getDataBaseObject(context: Context): MyDatabase {
            if (myDatabase == null) {
                myDatabase = Room.databaseBuilder(context, MyDatabase::class.java, "NotesDB")
                    .build()
            }
            return myDatabase!!
        }
    }

    // callback is used to add data in the database while we create in for the first time.
    /*.addCallback(object : RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            GlobalScope.launch {
                                myDatabase?.getDao()?.insertNote(Notes(0, "Aditya Dey ", "Hey how are you man , I am good"))
                                myDatabase?.getDao()?.insertNote(Notes(0, "LeetCode", "Leetcode is a platform for beginners to be a good coder"))
                            }
                        }
                    })*/
}

