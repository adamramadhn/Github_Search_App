package com.example.consumerapp

import android.database.Cursor

object MappingHelper {
    fun mapCursorToArray(cursor: Cursor?): ArrayList<User>{
        val list = ArrayList<User>()
        if (cursor != null){
            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUsersColumn.ID))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUsersColumn.USERNAME))
                val avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUsersColumn.AVATAR_URL))
                list.add(
                    User(
                        id,
                        avatarUrl,
                        username
                    )
                )
            }
        }
        return list
    }
}