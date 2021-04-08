package com.example.submission3.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.R
import com.example.submission3.adapters.ListUserAdapter
import com.example.submission3.databinding.FragmentFollowBinding
import com.example.submission3.viewmodels.FollowerFragmentViewModel

class FollowerFragment : Fragment(R.layout.fragment_follow) {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowerFragmentViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.adapter = adapter
        }
        showLoading(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowerFragmentViewModel::class.java
        )
        viewModel.listFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner, {
            if (it != null && it.isNotEmpty()) {
                adapter.setList(it)
                showLoading(false)
                isUserFound(true)
            }
            else {
                adapter.setList(it)
                showLoading(false)
                isUserFound(false)
            }
        })

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}