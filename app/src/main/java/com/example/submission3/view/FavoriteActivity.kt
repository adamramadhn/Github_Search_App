package com.example.submission3.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.R
import com.example.submission3.adapters.ListUserAdapter
import com.example.submission3.databinding.ActivityFavoriteBinding
import com.example.submission3.repositories.localdatasource.FavoriteUser
import com.example.submission3.repositories.models.User
import com.example.submission3.viewmodels.FavoriteActivityViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: FavoriteActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_fav_white)
        supportActionBar?.setTitle(R.string.favorite)
        showLoading(true)
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this).get(
            FavoriteActivityViewModel::class.java
        )
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter
        }
        viewModel.getFavUser()?.observe(this, {
            if (it != null && it.isNotEmpty()) {
                val list = mapList(it)
                adapter.setList(list)
                isUserFound(true)
            }
            else {
                val list = mapList(it)
                adapter.setList(list)
                showLoading(false)
                isUserFound(false)
            }
            showLoading(false)
        })
    }

    private fun isUserFound(state: Boolean) {
        if (state) {
            binding.errMsg.visibility = View.GONE
        } else {
            binding.errMsg.visibility = View.VISIBLE
        }
    }
    private fun mapList(users: List<FavoriteUser>?): ArrayList<User> {
        val listUser = ArrayList<User>()
        if (users != null) {
            for (user in users) {
                val userMapped = User(
                    user.id,
                    user.avatar_url,
                    user.login
                )
                listUser.add(userMapped)
            }
        }
        return listUser
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}