package com.shivamsatija.gituserrepositories.ui.listing

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shivamsatija.gituserrepositories.data.DataManager
import com.shivamsatija.gituserrepositories.data.model.User
import com.shivamsatija.gituserrepositories.util.RxSchedulersOverrideRule
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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

    private val repositoriesMvpView: RepositoriesMvpView = mock()

    private lateinit var presenter: RepositoriesPresenter<RepositoriesMvpView>

    @Before
    fun setUp() {
        presenter = RepositoriesPresenter(dataManager, disposable)
        presenter.attachView(repositoriesMvpView)
    }

    @Test
    fun shouldSearchUserFromRemote() {
        // Haven't fetched from network yet
        whenever(dataManager.searchUser(TEST_USER_NAME))
            .doReturn(Single.just(user))

        presenter.searchUser(TEST_USER_NAME)

        verify(dataManager).searchUser(TEST_USER_NAME)
        verify(repositoriesMvpView).setUser(user)
    }
}