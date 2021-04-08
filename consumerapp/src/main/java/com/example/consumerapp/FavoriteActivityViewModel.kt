package com.example.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavoriteActivityViewModel(application: Application) : AndroidViewModel(application) {
    private var list = MutableLiveData<ArrayList<User>>()

    fun favoriteUser(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteUsersColumn.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val listConverted = MappingHelper.mapCursorToArray(cursor)
        list.postValue(listConverted)
    }
    fun getFavUser(): LiveData<ArrayList<User>>?{
        return list
    }
}