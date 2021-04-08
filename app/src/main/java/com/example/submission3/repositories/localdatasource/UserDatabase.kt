package com.example.submission3.repositories.localdatasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class UserDatabase :RoomDatabase(){
    companion object{
        var Instance: UserDatabase? = null
        fun getDb(context:Context):UserDatabase?{
            if(Instance==null){
                synchronized(UserDatabase::class.java){
                    Instance= Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "userDb"
                    ).build()
                }
            }
            return Instance
        }
    }
    abstract fun favUserDao():FavoriteUserDao
}