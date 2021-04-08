package com.example.submission3.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission3.repositories.models.Response
import com.example.submission3.repositories.models.User
import com.example.submission3.repositories.remotedatasource.GHService
import retrofit2.Call
import retrofit2.Callback

class MainActivityViewModel : ViewModel() {
    object IsFail {
        var IS_FAIL = true
    }

    val listUsers = MutableLiveData<ArrayList<User>>()
    fun searchUsers(query: String) {
        GHService.apiInstance?.getUsers(query)?.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    IsFail.IS_FAIL = false
                    listUsers.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                IsFail.IS_FAIL = true
                Log.i("Message", "Terjadi Kesalahan!")
            }

        })
    }

    fun getUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }

}