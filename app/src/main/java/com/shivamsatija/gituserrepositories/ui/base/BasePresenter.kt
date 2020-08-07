package com.shivamsatija.gituserrepositories.ui.base

open class BasePresenter<V : BaseMvpView> : BaseMvpPresenter<V> {

    private var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun getView(): V? = view

    override fun detachView() {
        this.view = null
    }

    override fun isViewAttached(): Boolean = view != null
}