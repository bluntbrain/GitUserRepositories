package com.shivamsatija.gituserrepositories.data.model
import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("login")
    var login: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("avatar_url")
    var avatarUrl: String? = null,
    @SerializedName("bio")
    var bio: String? = null,
    @SerializedName("blog")
    var blog: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("followers")
    var followers: Int? = null,
    @SerializedName("followers_url")
    var followersUrl: String? = null,
    @SerializedName("html_url")
    var htmlUrl: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("location")
    var location: String? = null,
    @SerializedName("public_repos")
    var publicRepos: Int? = null,
    @SerializedName("repos_url")
    var reposUrl: String? = null
)