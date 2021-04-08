package com.example.submission3.repositories.remotedatasource

import com.example.submission3.repositories.models.Response
import com.example.submission3.repositories.models.ResponseDetail
import com.example.submission3.repositories.models.User
import retrofit2.Call
import retrofit2.http.*

interface GHInterface {
    @GET("/search/users")
    @Headers("Authorization: token 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")
    fun getUsers(@Query("q") q: String) : Call<Response>

    @GET("/users/{username}")
    @Headers("Authorization: token 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")
    fun getDetail(@Path("username") username: String) : Call<ResponseDetail>

    @GET("/users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String) : Call<ArrayList<User>>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username")username: String): Call<ArrayList<User>>

}