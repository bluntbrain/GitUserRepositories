package com.shivamsatija.gituserrepositories.ui.listing

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shivamsatija.gituserrepositories.R
import com.shivamsatija.gituserrepositories.data.model.Repository
import com.shivamsatija.gituserrepositories.data.model.User
import com.shivamsatija.gituserrepositories.di.component.ActivityComponent
import com.shivamsatija.gituserrepositories.ui.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_repositories_listing.*
import kotlinx.android.synthetic.main.layout_user_detail.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepositoriesListingActivity
    : BaseActivity(), RepositoriesMvpView {

    @Inject
    lateinit var presenter: RepositoriesMvpPresenter<RepositoriesMvpView>

    private var adapter: RepositoriesAdapter? = null

    override fun getLayoutResource(): Int = R.layout.activity_repositories_listing

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    private var disposable: CompositeDisposable = CompositeDisposable()
    private var searchSubject: PublishSubject<String?> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)

        setupToolbar()
        setupSearch()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun setupSearch() {
        etSearchUser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchSubject.onNext(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        disposable.add(
            searchSubject.debounce(250, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ query ->
                    query?.let {
                        presenter.searchUser(query)
                    } ?: run {
                        onUserSearchFailed()
                    }
                }, {

                })
        )
    }

    private fun setupRecyclerView() {

        adapter = RepositoriesAdapter(ArrayList(0))

        rvRepositories.layoutManager = LinearLayoutManager(this)
        rvRepositories.adapter = adapter
        rvRepositories.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
                ContextCompat.getDrawable(
                    this@RepositoriesListingActivity,
                    R.drawable.drawable_divider
                )?.let {
                    setDrawable(it)
                }
            }
        )

    }

    override fun setUser(user: User) {
        cvUserDetail.visibility = View.VISIBLE
        bindUser(user)
        presenter.fetchUserRepositories(user.login!!)
    }

    override fun onUserSearchFailed() {
        cvUserDetail.visibility = View.GONE
        adapter?.repositories?.clear()
    }

    override fun updateUserRepositories(repositories: ArrayList<Repository>) {
        adapter?.addRepositories(true, repositories)
    }

    override fun onFetchUserRepositoriesFailed() {

    }

    private fun bindUser(user: User) {

        Glide.with(ivUserImage.context)
            .load(user.avatarUrl)
            .apply(RequestOptions().circleCrop())
            .into(ivUserImage)

        tvUserName.text = user.name
        tvUserBlog.text = user.blog
        tvUserLocation.text = user.location
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        presenter.detachView()
    }
}