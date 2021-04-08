package com.example.submission3.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submission3.repositories.localdatasource.FavoriteUser
import com.example.submission3.repositories.localdatasource.FavoriteUserDao
import com.example.submission3.repositories.localdatasource.UserDatabase
import com.example.submission3.repositories.models.ResponseDetail
import com.example.submission3.repositories.remotedatasource.GHService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class DetailUserActivityViewModel(application: Application) : AndroidViewModel(application){
    object IsFail {
        var IS_FAIL = true
    }
    val user = MutableLiveData<ResponseDetail>()

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase? = UserDatabase.getDb(application)

    init {
        userDao = userDb?.favUserDao()
    }

    fun userDetail(query: String) {
        GHService.apiInstance?.getDetail(query)?.enqueue(object : Callback<ResponseDetail> {
            override fun onResponse(
                call: Call<ResponseDetail>,
                response: retrofit2.Response<ResponseDetail>
            ) {
                if(response.isSuccessful){
                    IsFail.IS_FAIL = false
                    user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                IsFail.IS_FAIL = true
                Log.i("Message","Terjadi Kesalahan!")
            }


        })
    }
    fun getUserDetail(): LiveData<ResponseDetail> {
        return user
    }

    fun addFav(username: String, id: Int, avatar: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                id,
                avatar
            )
            userDao?.addFavorite(user)
        }
    }
    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFav(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFavUser(id)
        }
    }
}