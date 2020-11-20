package com.sertancanpolat.downloaderforinsta.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.utilities.getShortCodeFromUrl
import com.sertancanpolat.downloaderforinsta.utilities.snackbarBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = getSystemService(InputMethodManager::class.java)
        ma_edTxtUserName.setOnEditorActionListener { t, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH && t.text.length > 2) {
                val intent: Intent
                val shortCode = getShortCodeFromUrl(t.text.toString())
                if(shortCode == " "){
                    intent = Intent(this, SearchResultActivity::class.java)
                    intent.putExtra("userName", t.text.toString())
                } else {
                    intent = Intent(this, PostDetailsActivity::class.java)
                    intent.putExtra("shortCode", shortCode)
                }

                startActivity(intent)
            } else snackbarBuilder(this, "Please enter the username, name or post url!")

            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}