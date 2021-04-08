package com.example.submission3.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.submission3.repositories.localdatasource.FavoriteUser
import com.example.submission3.repositories.localdatasource.FavoriteUserDao
import com.example.submission3.repositories.localdatasource.UserDatabase

class FavoriteActivityViewModel(application: Application):AndroidViewModel(application) {
    private var userDao : FavoriteUserDao?
    private var userDb: UserDatabase? = UserDatabase.getDb(application)

    init {
        userDao = userDb?.favUserDao()

    }
    fun getFavUser():LiveData<List<FavoriteUser>>?{
        return userDao?.getFavUser()
    }

}