package com.example.submission3.repositories.localdatasource

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_db")
    fun getFavUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite_db WHERE favorite_db.id=:id")
    suspend fun checkUser(id:Int):Int

    @Query("DELETE FROM favorite_db WHERE favorite_db.id=:id")
    suspend fun deleteFavUser(id:Int):Int

    @Query("SELECT * FROM favorite_db")
    fun findAll():Cursor
}