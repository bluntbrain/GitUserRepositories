package com.shivamsatija.gituserrepositories.data

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.shivamsatija.gituserrepositories.data.model.Repository
import com.shivamsatija.gituserrepositories.data.model.User
import com.shivamsatija.gituserrepositories.data.remote.ApiService
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class DataManagerTest {

    companion object {
        private const val TEST_USER_NAME = "developer-shivam"
    }

    private val apiService: ApiService = mock()

    private val user: User = mock()

    private val repository1: Repository = mock()
    private val repository2: Repository = mock()

    private lateinit var dataManager: DataManager

    @Before
    fun setUp() {
        // Initialize subject under test with mock dependencies
        dataManager = DataManager(apiService)
    }

    @Test
    fun shouldSearchUser() {
        whenever(apiService.searchUser(TEST_USER_NAME))
            .doReturn(Single.just(user))

        val observer = dataManager.searchUser(TEST_USER_NAME).test()
        observer.assertComplete()
    }

    @Test
    fun shouldFetchUserRepositories() {
        whenever(apiService.fetchRepositories(TEST_USER_NAME))
            .doReturn(Single.just(arrayListOf(repository1, repository2)))

        val observer = dataManager.fetchUserRepositories(TEST_USER_NAME).test()
        observer.assertComplete()
    }
}