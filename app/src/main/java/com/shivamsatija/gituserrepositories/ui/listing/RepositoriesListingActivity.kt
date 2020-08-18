package com.shivamsatija.gituserrepositories.ui.listing

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var currentUser: User? = null

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
            searchSubject.debounce(750, TimeUnit.MILLISECONDS)
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

        adapter = RepositoriesAdapter(ArrayList(0)) { url ->
            openUrl(url)
        }

        rvRepositories.apply {
            layoutManager = LinearLayoutManager(this@RepositoriesListingActivity)
            adapter = this@RepositoriesListingActivity.adapter
            addItemDecoration(DividerItemDecoration(this@RepositoriesListingActivity, DividerItemDecoration.VERTICAL).apply {
                ContextCompat.getDrawable(
                    this@RepositoriesListingActivity,
                    R.drawable.drawable_divider
                )?.let {
                    setDrawable(it)
                }
            })
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == recyclerView.adapter?.itemCount
                        && !presenter.isDataLoading() && presenter.isMoreData()) {
                        currentUser?.let { currentUser ->
                            presenter.fetchUserRepositories(currentUser.login!!, false)
                        }
                    }
                }
            })
        }
    }

    override fun setUser(user: User) {
        currentUser = user
        cvUserDetail.visibility = View.VISIBLE
        bindUser(user)
        presenter.fetchUserRepositories(user.login!!, true)
    }

    override fun onUserSearchFailed() {
        cvUserDetail.visibility = View.GONE
        adapter?.repositories?.clear()
    }

    override fun updateUserRepositories(repositories: ArrayList<Repository>, toClear: Boolean) {
        adapter?.addRepositories(toClear, repositories)
    }

    override fun onFetchUserRepositoriesFailed() {
        if (adapter?.itemCount == 0) {
            showToast("No repositories found for this user.")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindUser(user: User) {

        Glide.with(ivUserImage.context)
            .load(user.avatarUrl)
            .apply(RequestOptions().circleCrop())
            .into(ivUserImage)

        tvUserName.text = if (TextUtils.isEmpty(user.name)) user.login else user.name
        tvUserBlog.text = if (TextUtils.isEmpty(user.blog)) user.htmlUrl else user.blog
        tvUserLocation.text = "${user.followers ?: 0} followers"
    }

    private fun openUrl(url: String?) {
        if (!TextUtils.isEmpty(url)) {
            try {
//                val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                startActivity(myIntent)
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(this, Uri.parse(url))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this,
                    "No application can handle this request."
                            + " Please install a web browser", Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        presenter.detachView()
    }
}