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

    override fun searchUser(username: String) {

        if (!isViewAttached()) return

        compositeDisposable.add(
            dataManager.searchUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getView()!!.setUser(it)
                }, {
                    getView()!!.onUserNotFound()
                })
        )
    }

    override fun fetchUserRepositories(username: String) {

    }

    override fun detachView() {
        super.detachView()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}