package com.sertancanpolat.downloaderforinsta.view

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.adapter.SearchResultAdapter
import com.sertancanpolat.downloaderforinsta.utilities.ProcessState
import com.sertancanpolat.downloaderforinsta.utilities.progressDialogBuilder
import com.sertancanpolat.downloaderforinsta.viewmodel.SearchResultViewModel
import com.sertancanpolat.downloaderforinsta.viewmodelFactory.SearchResultViewModelFactory
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchResultViewModel
    private lateinit var progressDialog: Dialog
    private lateinit var bannerAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val userName = intent.getStringExtra("userName")!!
        val viewModelFactory = SearchResultViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchResultViewModel::class.java)

        observeLiveData()
        viewModel.searchUser(userName)

        progressDialog = progressDialogBuilder(this)

        MobileAds.initialize(this) {}
        bannerAdView = sra_banner_ad
        val adRequest = AdRequest.Builder().build()
        bannerAdView.loadAd(adRequest)
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