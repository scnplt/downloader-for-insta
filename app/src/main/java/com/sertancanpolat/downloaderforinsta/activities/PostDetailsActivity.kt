package com.sertancanpolat.downloaderforinsta.activities

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sertancanpolat.downloaderforinsta.adapters.PostDetailsAdapter
import com.sertancanpolat.downloaderforinsta.databinding.ActivityPostDetailsBinding
import com.sertancanpolat.downloaderforinsta.utils.*
import com.sertancanpolat.downloaderforinsta.viewmodels.PostDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostDetailsActivity : AppCompatActivity() {
    @Inject lateinit var adapter: PostDetailsAdapter
    private val viewModel: PostDetailsViewModel by viewModels()

    private lateinit var shortCode: String
    private lateinit var progressDialog: Dialog
    private lateinit var binding: ActivityPostDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shortCode = if (intent.action == Intent.ACTION_SEND) {
            var url = intent.getStringExtra(Intent.EXTRA_TEXT)!!.toString()
            url = url.subSequence(url.indexOf("http"), url.lastIndex).toString()
            getShortCodeFromUrl(url)
        } else intent.getStringExtra("shortCode")!!

        observeLiveData()
        progressDialog = this.progressDialog()

        viewModel.getPost(shortCode)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
        progressDialog.dismiss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun observeLiveData() {
        viewModel.processState.observe(this, { state ->
            when (state) {
                ProcessState.IDLE -> {
                    binding.pdaRvPostDetails.visibility = View.GONE
                    binding.pdaTxtViewError.visibility = View.GONE
                    progressDialog.cancel()
                }
                ProcessState.LOADING -> progressDialog.show()
                ProcessState.ERROR -> {
                    binding.pdaRvPostDetails.visibility = View.GONE
                    binding.pdaTxtViewError.visibility = View.VISIBLE
                    progressDialog.cancel()
                }
                ProcessState.LOADED -> {
                    binding.pdaTxtViewError.visibility = View.GONE
                    binding.pdaRvPostDetails.adapter = adapter.apply { postModel = viewModel.postModel.value!! }
                    binding.pdaRvPostDetails.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.pdaRvPostDetails.visibility = View.VISIBLE
                    progressDialog.cancel()
                }
                else -> {
                }
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                downloadFile(this, adapter.downloadUrl, adapter.isVideo)
            else this.snackBar("Please enable storage access permission.", 18f)
        }
    }
}