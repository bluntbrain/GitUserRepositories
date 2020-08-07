package com.shivamsatija.gituserrepositories.data.remote

import com.shivamsatija.gituserrepositories.data.model.Repository
import com.shivamsatija.gituserrepositories.data.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users/{username}")
    fun searchUser(
        @Path("username") username: String
    ) : Single<User>

    @GET("users/{username}/repos")
    fun fetchRepositories(
        @Path("username") username: String,
        @Query("type") type: String = "\"owner\"",
        @Query("sort") sort: String = "\"updated\""
    ) : Single<ArrayList<Repository>>
}