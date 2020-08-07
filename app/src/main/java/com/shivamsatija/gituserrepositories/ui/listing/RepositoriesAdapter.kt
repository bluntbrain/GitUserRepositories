package com.shivamsatija.gituserrepositories.ui.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shivamsatija.gituserrepositories.R
import com.shivamsatija.gituserrepositories.data.model.Repository
import kotlinx.android.synthetic.main.layout_item_user_repository.view.*

class RepositoriesAdapter(
    var repositories: ArrayList<Repository>,
    var interaction: (String?) -> Unit
) : RecyclerView.Adapter<RepositoriesAdapter.RepositoryViewHolder>() {

    fun addRepositories(toClear: Boolean, repositories: ArrayList<Repository>) {
        if (toClear) {
            this@RepositoriesAdapter.repositories.clear()
        }
        this@RepositoriesAdapter.repositories.addAll(repositories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_user_repository, parent, false)
        )
    }

    override fun getItemCount(): Int = repositories.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bindRepository(repositories[position])

        holder.itemView.setOnClickListener {
            interaction(repositories[position].htmlUrl)
        }
    }

    class RepositoryViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        fun bindRepository(repository: Repository) {
            itemView.tvRepositoryName.text = repository.name
            itemView.tvRepositoryFullName.text = repository.fullName
            itemView.tvRepositoryDescription.text = repository.description
            itemView.tvStars.text = "${repository.stargazersCount}"
            itemView.tvForks.text = "${repository.forksCount}"

            repository.license?.let {
                itemView.chipLicence.visibility = View.VISIBLE
                itemView.chipLicence.text = it.name
            } ?: run {
                itemView.chipLicence.visibility = View.GONE
            }
        }
    }
}