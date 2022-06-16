package com.example.mynotes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CRU_Note : AppCompatActivity() {
    var noteId: Int = -1
    private lateinit var title_editText: EditText
    private lateinit var text_editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cru_note)
        val changeTitle = intent.getStringExtra("title")
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        supportActionBar?.title = changeTitle

        title_editText = findViewById(R.id.title)
        text_editText = findViewById(R.id.textNote)

        val noteTitle = intent.getStringExtra("noteTitle")
        val noteText = intent.getStringExtra("noteText")
        this.noteId = intent.getIntExtra("noteId", -1)
        if(intent != null && intent.action.equals(Intent.ACTION_SEND)){
            this.noteId = -100
            text_editText.setText(intent.getStringExtra(Intent.EXTRA_TEXT))
        }
        else{
            if(noteTitle != null){
                title_editText.setText("$noteTitle")
            }
            if(noteText != null){
                text_editText.setText("$noteText")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolber_cru_note, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.share -> shareData()
            R.id.save -> saveData()
            else ->  finish()
        }
        return true
    }

    private fun shareData() {
        val getTitle = title_editText.text.toString()
        val getNote = text_editText.text.toString()

        if(getTitle.isNotEmpty() || getNote.isNotEmpty()){
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "$getTitle\n     $getNote")
                startActivity(this)
            }
        }else{
            Toast.makeText(this,"Please write your Notes", Toast.LENGTH_SHORT).show()
        }
    }

    @DelicateCoroutinesApi
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            saveData()
        }
        return super.onKeyDown(keyCode, event)
    }


    @SuppressLint("ResourceType")
    private fun saveData(){
        val getTitle = title_editText.text.toString()
        val getNote = text_editText.text.toString()

        if(getTitle.isNotEmpty() || getNote.isNotEmpty()){

            if(this.noteId == -100){
                val bundle = Bundle()

                bundle.putString("title", getTitle)
                bundle.putString("text", getNote)
                val homeFragment = HomeFragment()
                val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                homeFragment.arguments = bundle
                fragmentTransaction.add(homeFragment, "").commit()
            }else{
                Intent().apply {
                    this.putExtra("id", noteId)
                    this.putExtra("title", getTitle)
                    this.putExtra("text", getNote)
                    setResult(RESULT_OK, this)
                }
            }
            Toast.makeText(this," Your Notes is Saved SuccessFully",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this," Your Notes is Not Saved",Toast.LENGTH_SHORT).show()
        }
        finish()
    }

}