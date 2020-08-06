package com.shivamsatija.gituserrepositories.ui.base

interface BaseMvpView {

    fun showLoading()

    fun hideLoading()

    fun showToast(message: String)
}