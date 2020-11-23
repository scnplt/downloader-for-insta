package com.sertancanpolat.downloaderforinsta.activities

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sertancanpolat.downloaderforinsta.adapters.SearchResultAdapter
import com.sertancanpolat.downloaderforinsta.databinding.ActivitySearchResultBinding
import com.sertancanpolat.downloaderforinsta.utils.ProcessState
import com.sertancanpolat.downloaderforinsta.utils.progressDialog
import com.sertancanpolat.downloaderforinsta.viewmodels.SearchResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchResultActivity : AppCompatActivity() {
    @Inject lateinit var adapter: SearchResultAdapter
    private val viewModel: SearchResultViewModel by viewModels()

    private lateinit var progressDialog: Dialog
    private lateinit var binding: ActivitySearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    binding.sraRvSearchUser.visibility = View.GONE
                    binding.sraTxtViewError.visibility = View.GONE
                    binding.sraTxtViewUserNotFound.visibility = View.GONE
                    progressDialog.cancel()
                }
                ProcessState.LOADING -> {
                    progressDialog.show()
                    binding.sraRvSearchUser.visibility = View.GONE
                    binding.sraTxtViewError.visibility = View.GONE
                    binding.sraTxtViewUserNotFound.visibility = View.GONE
                }
                ProcessState.ERROR -> {
                    binding.sraTxtViewUserNotFound.visibility = View.GONE
                    binding.sraRvSearchUser.visibility = View.GONE
                    binding.sraTxtViewError.visibility = View.VISIBLE
                    progressDialog.cancel()
                }
                ProcessState.LOADED -> {
                    if(viewModel.searchResult.value?.users?.size == 0){
                        binding.sraTxtViewUserNotFound.visibility = View.VISIBLE
                    }else {
                        binding.sraTxtViewError.visibility = View.GONE
                        binding.sraTxtViewUserNotFound.visibility = View.GONE
                        binding.sraRvSearchUser.adapter = adapter.apply { searchedUserModel = viewModel.searchResult.value!! }
                        binding.sraRvSearchUser.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        binding.sraRvSearchUser.visibility = View.VISIBLE
                    }
                    progressDialog.cancel()
                }
                else -> {}
            }
        })
    }
}