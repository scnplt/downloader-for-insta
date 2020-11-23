package com.sertancanpolat.downloaderforinsta.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.sertancanpolat.downloaderforinsta.R
import com.sertancanpolat.downloaderforinsta.adapter.UserPostsAdapter
import com.sertancanpolat.downloaderforinsta.databinding.ActivityUserBinding
import com.sertancanpolat.downloaderforinsta.utilities.ProcessState
import com.sertancanpolat.downloaderforinsta.utilities.progressDialog
import com.sertancanpolat.downloaderforinsta.viewmodel.UserViewModel
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

        observeLiveData()
        viewModel.getUser(userName)

        binding.toolbar.title = "@$userName"
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        progressDialog = this.progressDialog()

        binding.uaRvPosts.setOnScrollChangeListener(recyclerViewListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        binding.toolbar.inflateMenu(R.menu.ua_menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.refreshData) startActivity(intent)
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
            when (state) {
                ProcessState.IDLE -> {
                    binding.uaInfoUserInfoLayout.visibility = View.GONE
                    binding.uaDataLayout.visibility = View.GONE
                    progressDialog.cancel()
                }
                ProcessState.LOADING -> {
                    binding.uaInfoUserInfoLayout.visibility = View.GONE
                    binding.uaDataLayout.visibility = View.GONE
                    progressDialog.show()
                }
                ProcessState.ERROR -> {
                    binding.uaTxtViewError.visibility = View.VISIBLE
                    binding.uaDataLayout.visibility = View.GONE
                    binding.uaInfoUserInfoLayout.visibility = View.GONE
                    progressDialog.cancel()
                }
                ProcessState.LOADED -> {
                    binding.uaTxtViewError.visibility = View.GONE
                    binding.uaInfoUserInfoLayout.visibility = View.VISIBLE
                    binding.uaDataLayout.visibility = View.VISIBLE
                    bindData()
                    if (viewModel.userModel.value?.graphql?.user?.isPrivate!!) {
                        binding.uaRvPosts.visibility = View.GONE
                        binding.uaTxtViewPrivate.visibility = View.VISIBLE
                    } else {
                        binding.uaTxtViewPrivate.visibility = View.GONE
                        binding.uaRvPosts.visibility = View.VISIBLE
                    }
                    progressDialog.cancel()
                }
                else -> { }
            }
        })

        viewModel.morePostState.observe(this, { state ->
            when (state) {
                ProcessState.LOADING -> progressDialog.show()
                ProcessState.ERROR -> {
                    binding.uaInfoUserInfoLayout.visibility = View.GONE
                    binding.uaDataLayout.visibility = View.GONE
                    binding.uaTxtViewError.visibility = View.VISIBLE
                    progressDialog.cancel()
                }
                ProcessState.LOADED -> {
                    adapter.notifyItemRangeInserted(
                        viewModel.lastMediaIndex.value!!,
                        viewModel.incomingMediaSize.value!!
                    )
                    viewModel.lastMediaIndex.value = viewModel.userModel.value?.graphql?.user?.edgeOwnerToTimelineMedia?.edges?.size
                    progressDialog.cancel()
                }
                else -> {
                }
            }
        })
    }

    private fun bindData() {
        val user = viewModel.userModel.value?.graphql?.user!!

        if (!user.isPrivate) {
            binding.uaRvPosts.adapter = adapter.apply { userModel = user }
            binding.uaRvPosts.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        }

        binding.userModel = user
    }

    private val recyclerViewListener = { _: View, _: Int, _: Int, _: Int, _: Int ->
        if (binding.uaRvPosts.computeVerticalScrollOffset() > binding.uaRvPosts.computeVerticalScrollRange() - 4000
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