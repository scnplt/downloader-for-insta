package com.sertancanpolat.downloaderforinsta.activities

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.sertancanpolat.downloaderforinsta.databinding.ActivityMainBinding
import com.sertancanpolat.downloaderforinsta.utils.getShortCodeFromUrl
import com.sertancanpolat.downloaderforinsta.utils.snackBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = getSystemService(InputMethodManager::class.java)

        binding.maEdTxtUserName.setOnEditorActionListener { t, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH && t.text.length > 2) {
                val intent: Intent
                val shortCode = getShortCodeFromUrl(t.text.toString())

                if (shortCode == " ") {
                    intent = Intent(this, SearchResultActivity::class.java)
                    intent.putExtra("userName", t.text.toString())
                } else {
                    intent = Intent(this, PostDetailsActivity::class.java)
                    intent.putExtra("shortCode", shortCode)
                }

                startActivity(intent)
            } else this.snackBar("Please enter the username, name or post url!")

            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}