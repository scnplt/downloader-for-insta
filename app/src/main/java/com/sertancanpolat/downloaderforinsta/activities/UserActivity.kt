package com.sertancanpolat.downloaderforinsta.activities

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.adapters.UserPostsAdapter
import com.sertancanpolat.downloaderforinsta.databinding.ActivityUserBinding
import com.sertancanpolat.downloaderforinsta.utils.ProcessState
import com.sertancanpolat.downloaderforinsta.utils.progressDialog
import com.sertancanpolat.downloaderforinsta.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    @Inject lateinit var adapter: UserPostsAdapter
    private val viewModel: UserViewModel by viewModels()

    private lateinit var progressDialog: Dialog
    private lateinit var userName: String
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user)

        userName = intent.getStringExtra("userName")!!
        binding.toolbar.title = "@$userName"
        observeLiveData()
        viewModel.getUser(userName)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        progressDialog = this.progressDialog()
        binding.rvPosts.setOnScrollChangeListener(recyclerViewListener)
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getUser(userName)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
        progressDialog.dismiss()
    }

    private fun observeLiveData() {
        viewModel.userState.observe(this, { state ->
            when (state!!) {
                ProcessState.IDLE -> showThisViews()
                ProcessState.LOADING -> {
                    showThisViews()
                    progressDialog.show()
                }
                ProcessState.ERROR -> {
                    showThisViews(binding.txtError)
                    progressDialog.cancel()
                }
                ProcessState.LOADED -> {
                    binding.txtError.visibility = View.GONE
                    binding.infoLayout.visibility = View.VISIBLE
                    binding.dataLayout.visibility = View.VISIBLE

                    val user = viewModel.userModel.value?.graphql?.user!!
                    binding.userModel = user

                    if (!user.isPrivate) {
                        binding.txtPrivate.visibility = View.GONE
                        binding.rvPosts.visibility = View.VISIBLE
                        binding.rvPosts.adapter = adapter.apply { userModel = user }
                        binding.rvPosts.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
                    } else {
                        binding.rvPosts.visibility = View.GONE
                        binding.txtPrivate.visibility = View.VISIBLE
                    }

                    if (user.edgeOwnerToTimelineMedia?.edges?.size == 0)
                        (binding.collapsingToolbar.layoutParams as AppBarLayout.LayoutParams).scrollFlags = SCROLL_FLAG_NO_SCROLL

                    progressDialog.cancel()
                }
            }
        })

        viewModel.morePostState.observe(this, { state ->
            when (state) {
                ProcessState.LOADING -> progressDialog.show()
                ProcessState.ERROR -> {
                    showThisViews(binding.txtError)
                    progressDialog.cancel()
                }
                ProcessState.LOADED -> {
                    adapter.notifyItemRangeInserted(
                        viewModel.lastMediaIndex.value!! + 1,
                        viewModel.incomingMediaSize.value!!
                    )
                    progressDialog.cancel()
                }
                else -> { }
            }
        })
    }

    private fun showThisViews(vararg views: View){
        binding.infoLayout.visibility = View.GONE
        binding.dataLayout.visibility = View.GONE
        binding.txtError.visibility = View.GONE

        views.forEach { it.visibility = View.VISIBLE }
    }

    private val recyclerViewListener = { _: View, _: Int, _: Int, _: Int, _: Int ->
        if (binding.rvPosts.computeVerticalScrollOffset() > binding.rvPosts.computeVerticalScrollRange() - 4000
            && viewModel.morePostState.value != ProcessState.LOADING
            && viewModel.userModel.value?.graphql?.user?.edgeOwnerToTimelineMedia?.pageInfo?.hasNextPage!!
        ) {
            viewModel.getMorePost(
                viewModel.userModel.value?.graphql?.user?.id!!,
                viewModel.userModel.value?.graphql?.user?.edgeOwnerToTimelineMedia?.pageInfo?.endCursor
            )
        }
    }
}