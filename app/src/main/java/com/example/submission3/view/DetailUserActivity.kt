package com.example.submission3.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission3.R
import com.example.submission3.adapters.SectionPagerAdapter
import com.example.submission3.databinding.ActivityDetailUserBinding
import com.example.submission3.viewmodels.DetailUserActivityViewModel
import com.example.submission3.viewmodels.MainActivityViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserActivityViewModel

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener(this)
        binding.shareBtn.setOnClickListener(this)


        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val sectionsPagerAdapter = SectionPagerAdapter(this, bundle)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        viewModel = ViewModelProvider(this).get(DetailUserActivityViewModel::class.java)
        showLoading(true)
        isFail()
        username?.let { viewModel.userDetail(it) }
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.errMsg2.visibility = View.GONE
                binding.apply {
                    Glide.with(this@DetailUserActivity).load(it.avatar_url).centerCrop()
                        .into(avatar)
                    tvname.text = it.name
                    nameTv.text = it.login
                    locationTv.text = it.location
                    companyTv.text = it.company
                    jfollowerTv.text = it.followers.toString()
                    jfollowingTv.text = it.following.toString()
                    jrepositoryTv.text = it.public_repos.toString()
                }
                showLoading(false)
            }
        })

        var isFav = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.favBtn.isChecked = true
                        isFav = true
                    } else {
                        binding.favBtn.isChecked = false
                        isFav = false
                    }
                }
            }
        }

        binding.favBtn.setOnClickListener {
            isFav = !isFav
            if (isFav) {
                if (username != null) {
                    if (avatarUrl != null) {
                        viewModel.addFav(username, id, avatarUrl)
                    }
                }
            } else {
                viewModel.removeFav(id)
            }
            binding.favBtn.isChecked = isFav
        }

    }
    private fun isFail() {
        if (MainActivityViewModel.IsFail.IS_FAIL) {
            showLoading(false)
            binding.errMsg2.visibility = View.VISIBLE
        }
        else{
            binding.errMsg2.visibility = View.VISIBLE
            showLoading(false)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back_btn -> finish()
            R.id.share_btn -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Github User Detail\nName: ${binding.tvname.text}\nUsername: ${binding.nameTv.text}\nLocation: ${binding.locationTv.text}\nWork at: ${binding.companyTv.text}"
                )
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
        }
    }

}

