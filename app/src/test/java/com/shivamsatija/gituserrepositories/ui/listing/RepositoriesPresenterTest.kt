package com.shivamsatija.gituserrepositories.ui.listing

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shivamsatija.gituserrepositories.data.DataManager
import com.shivamsatija.gituserrepositories.data.model.Repository
import com.shivamsatija.gituserrepositories.data.model.User
import com.shivamsatija.gituserrepositories.util.RxSchedulersOverrideRule
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyObject

class RepositoriesPresenterTest {

    companion object {
        private const val TEST_USER_NAME = "developer-shivam"
    }

    @JvmField
    @Rule
    var schedulersOverrideRule = RxSchedulersOverrideRule()

    private val dataManager: DataManager = mock()

    private val disposable: CompositeDisposable = mock()

    private val user: User = mock()

    private val repository1: Repository = mock()
    private val repository2: Repository = mock()

    private val repositoriesMvpView: RepositoriesMvpView = mock()

    private lateinit var presenter: RepositoriesPresenter<RepositoriesMvpView>

    @Before
    fun setUp() {
        presenter = RepositoriesPresenter(dataManager, disposable)
        presenter.attachView(repositoriesMvpView)
    }

    @Test
    fun shouldSearchUser() {
        whenever(dataManager.searchUser(TEST_USER_NAME))
            .doReturn(Single.just(user))

        presenter.searchUser(TEST_USER_NAME)

        verify(dataManager).searchUser(TEST_USER_NAME)
        verify(repositoriesMvpView).setUser(user)
    }

    @Test
    fun shouldFetchUserRepositories() {

        val sampleResponse = arrayListOf(repository1, repository2)

        whenever(dataManager.fetchUserRepositories(TEST_USER_NAME, 0 ,10))
            .doReturn(Single.just(sampleResponse))

        presenter.fetchUserRepositories(TEST_USER_NAME, true)

        verify(dataManager).fetchUserRepositories(TEST_USER_NAME, 0 ,10)
        verify(repositoriesMvpView).updateUserRepositories(sampleResponse, true)
    }
}