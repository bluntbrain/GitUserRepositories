package com.shivamsatija.gituserrepositories.ui.base

interface BaseMvpPresenter<V : BaseMvpView> {

    fun attachView(view: V)

    fun getView(): V?

    fun detachView()

    fun isViewAttached(): Boolean
}