package com.sertancanpolat.downloaderforinsta.view

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.adapter.SearchResultAdapter
import com.sertancanpolat.downloaderforinsta.utilities.ProcessState
import com.sertancanpolat.downloaderforinsta.utilities.progressDialog
import com.sertancanpolat.downloaderforinsta.viewmodel.SearchResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_search_result.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchResultActivity : AppCompatActivity() {
    private val viewModel: SearchResultViewModel by viewModels()

    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val userName = intent.getStringExtra("userName")!!

        observeLiveData()
        viewModel.searchUser(userName)

        progressDialog = this.progressDialog()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialog.dismiss()
        viewModel.clear()
    }

    private fun observeLiveData() {
        viewModel.processState.observe(this, { state ->
            when (state) {
                ProcessState.IDLE -> {
                    sra_rvSearchUser.visibility = View.GONE
                    sra_txtViewError.visibility = View.GONE
                    sra_txtViewUserNotFound.visibility = View.GONE
                    progressDialog.cancel()
                }
                ProcessState.LOADING -> {
                    progressDialog.show()
                    sra_rvSearchUser.visibility = View.GONE
                    sra_txtViewError.visibility = View.GONE
                    sra_txtViewUserNotFound.visibility = View.GONE
                }
                ProcessState.ERROR -> {
                    sra_txtViewUserNotFound.visibility = View.GONE
                    sra_rvSearchUser.visibility = View.GONE
                    sra_txtViewError.visibility = View.VISIBLE
                    progressDialog.cancel()
                }
                ProcessState.LOADED -> {
                    if(viewModel.searchResult.value?.users?.size == 0){
                        sra_txtViewUserNotFound.visibility = View.VISIBLE
                    }else {
                        sra_txtViewError.visibility = View.GONE
                        sra_txtViewUserNotFound.visibility = View.GONE
                        sra_rvSearchUser.adapter = SearchResultAdapter(viewModel.searchResult.value!!)
                        sra_rvSearchUser.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        sra_rvSearchUser.visibility = View.VISIBLE
                    }
                    progressDialog.cancel()
                }
                else -> {}
            }
        })
    }
}