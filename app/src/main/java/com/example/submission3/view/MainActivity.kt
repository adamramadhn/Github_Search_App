package com.example.submission3.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.R
import com.example.submission3.adapters.ListUserAdapter
import com.example.submission3.databinding.ActivityMainBinding
import com.example.submission3.repositories.models.User
import com.example.submission3.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: ListUserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_github_logo)
        supportActionBar?.setTitle(R.string.app_name)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        showLoading(false)
        binding.errMsg.visibility = View.INVISIBLE
        binding.errMsg2.visibility = View.GONE
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainActivityViewModel::class.java
        )
        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter

            btnSearch.setOnClickListener {
                searchUsr()
            }
            sv.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    isUserFound(true)
                    searchUsr()
                    closeKeyBoard()
                    isFail()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

        }
        viewModel.getUsers().observe(this, {
            if (it != null && it.isNotEmpty()) {
                binding.errMsg2.visibility = View.GONE
                adapter.setList(it)
                showLoading(false)
            } else {
                binding.errMsg2.visibility = View.GONE
                adapter.setList(it)
                showLoading(false)
                isUserFound(false)
            }

        })

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

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    private fun searchUsr() {
        binding.apply {
            val query = sv.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.searchUsers(query)
        }
    }

    private fun isUserFound(state: Boolean) {
        if (state) {
            binding.errMsg.visibility = View.GONE
        } else {
            binding.errMsg.visibility = View.VISIBLE
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_menu -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.setting -> {
                Intent(this, SettingsActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}