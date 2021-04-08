package com.example.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: FavoriteActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        binding.progressBar.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this).get(FavoriteActivityViewModel::class.java)
        binding.errMsg.visibility = View.INVISIBLE
        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter
        }
        viewModel.favoriteUser(this)

        viewModel.getFavUser()?.observe(this, Observer {
            if(it != null && it.isNotEmpty()){
                adapter.setList(it)
                binding.progressBar.visibility = View.GONE
            }
            else{
                binding.progressBar.visibility = View.GONE
                binding.errMsg.visibility = View.VISIBLE
            }
        })
    }
}