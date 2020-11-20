package com.sertancanpolat.downloaderforinsta.view

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.adapter.PostDetailsAdapter
import com.sertancanpolat.downloaderforinsta.utilities.*
import com.sertancanpolat.downloaderforinsta.viewmodel.PostDetailsViewModel
import com.sertancanpolat.downloaderforinsta.viewmodelFactory.PostDetailsViewModelFactory
import kotlinx.android.synthetic.main.activity_post_details.*

class PostDetailsActivity : AppCompatActivity() {
    private lateinit var shortCode: String
    private lateinit var viewModel: PostDetailsViewModel
    private lateinit var progressDialog: Dialog
    private lateinit var postAdapter: PostDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        shortCode = if (intent.action == Intent.ACTION_SEND) {
            var url = intent.getStringExtra(Intent.EXTRA_TEXT)!!.toString()
            url = url.subSequence(url.indexOf("http"), url.lastIndex).toString()
            getShortCodeFromUrl(url)
        } else intent.getStringExtra("shortCode")!!

        val viewModelFactory = PostDetailsViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostDetailsViewModel::class.java)

        observeLiveData()
        progressDialog = progressDialogBuilder(this)

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
                    pda_rvPostDetails.visibility = View.GONE
                    pda_txtViewError.visibility = View.GONE
                    progressDialog.cancel()
                }
                ProcessState.LOADING -> progressDialog.show()
                ProcessState.ERROR -> {
                    pda_rvPostDetails.visibility = View.GONE
                    pda_txtViewError.visibility = View.VISIBLE
                    progressDialog.cancel()
                }
                ProcessState.LOADED -> {
                    pda_txtViewError.visibility = View.GONE
                    postAdapter = PostDetailsAdapter(viewModel.postModel.value!!)
                    pda_rvPostDetails.adapter = postAdapter
                    pda_rvPostDetails.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    pda_rvPostDetails.visibility = View.VISIBLE
                    progressDialog.cancel()
                }
                else -> {
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                downloadFile(this, postAdapter.downloadUrl, postAdapter.postIsVideo)
            else snackbarBuilder(this, "Please enable storage access permission.", 18f)
        }
    }
}