package com.example.submission3.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission3.repositories.models.User
import com.example.submission3.repositories.remotedatasource.GHService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragmentViewModel :ViewModel() {
    val followersList = MutableLiveData<ArrayList<User>>()

    fun listFollowers(query: String) {
        GHService.apiInstance?.getFollowers(query)?.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if(response.isSuccessful){
                    followersList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
               Log.d("Message","Terjadi Kesalahan")
            }


        })
    }
    fun getListFollowers(): LiveData<ArrayList<User>> {
        return followersList
    }
}