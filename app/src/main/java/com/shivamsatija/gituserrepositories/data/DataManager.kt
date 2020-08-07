package com.shivamsatija.gituserrepositories.data

import com.shivamsatija.gituserrepositories.data.model.Repository
import com.shivamsatija.gituserrepositories.data.model.User
import com.shivamsatija.gituserrepositories.data.remote.ApiService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(
    private val service: ApiService
) {

    fun searchUser(username: String) : Single<User>
            = service.searchUser(username)

    fun fetchUserRepositories(username: String) : Single<ArrayList<Repository>>
            = service.fetchRepositories(username)
}