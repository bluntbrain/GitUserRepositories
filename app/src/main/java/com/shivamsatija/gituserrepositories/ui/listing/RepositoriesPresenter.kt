package com.shivamsatija.gituserrepositories.ui.listing

import com.shivamsatija.gituserrepositories.data.DataManager
import com.shivamsatija.gituserrepositories.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepositoriesPresenter<V : RepositoriesMvpView> @Inject constructor(
    var dataManager: DataManager,
    var compositeDisposable: CompositeDisposable
) : BasePresenter<V>(), RepositoriesMvpPresenter<V> {

    var page = 0
    var countPerPage = 10
    private var hasMoreData = true
    private var isDataLoading = false

    override fun searchUser(username: String) {

        if (!isViewAttached()) return

        compositeDisposable.add(
            dataManager.searchUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getView()!!.setUser(it)
                }, {
                    getView()!!.onUserSearchFailed()
                })
        )
    }

    override fun fetchUserRepositories(username: String, toClear: Boolean) {

        if (!isViewAttached()) return

        setDataLoading(true)

        if (toClear) {
            resetValues()
        }

        compositeDisposable.add(
            dataManager.fetchUserRepositories(username, page, countPerPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ repositories ->
                    setDataLoading(false)
                    if (repositories.size < countPerPage) {
                        setHasMoreData(false)
                    } else {
                        setHasMoreData(true)
                        page++
                    }
                    getView()!!.updateUserRepositories(repositories, toClear)
                }, {
                    getView()!!.onFetchUserRepositoriesFailed()
                })
        )
    }

    override fun detachView() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.detachView()
    }

    private fun resetValues() {
        page = 0
        setHasMoreData(true)
    }

    override fun isMoreData(): Boolean = hasMoreData

    override fun setHasMoreData(moreData: Boolean) {
        hasMoreData = moreData
    }

    override fun isDataLoading(): Boolean = isDataLoading

    override fun setDataLoading(dataLoading: Boolean) {
        isDataLoading = dataLoading
    }
}