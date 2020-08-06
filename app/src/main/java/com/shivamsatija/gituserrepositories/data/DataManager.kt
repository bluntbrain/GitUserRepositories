package com.shivamsatija.gituserrepositories.data

import com.shivamsatija.gituserrepositories.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(
    private val service: ApiService
) {

}