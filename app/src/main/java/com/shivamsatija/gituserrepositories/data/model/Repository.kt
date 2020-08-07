package com.shivamsatija.gituserrepositories.data.model
import com.google.gson.annotations.SerializedName


data class Repository(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("created_at")
    var createdAt: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("full_name")
    var fullName: String? = null,
    @SerializedName("git_url")
    var gitUrl: String? = null,
    @SerializedName("homepage")
    var homepage: String? = null,
    @SerializedName("language")
    var language: String? = null,
    @SerializedName("license")
    var license: License? = null,
    var mirrorUrl: Any? = null,
    @SerializedName("owner")
    var owner: User? = null,
    @SerializedName("stargazers_count")
    var stargazersCount: Int = 0,
    @SerializedName("watchers_count")
    var watchersCount: Int = 0,
    @SerializedName("forks_count")
    var forksCount: Int? = null,
    @SerializedName("html_url")
    var htmlUrl: String? = null,
    @SerializedName("stargazers_url")
    var stargazersUrl: String? = null,
    @SerializedName("url")
    var url: String? = null
) {

    data class License(
        @SerializedName("name")
        var name: String? = null
    )
}