package com.example.stgoa.gistlib.gist.service.model

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap

data class GistResponse(
    @SerializedName("comments")
    val comments: Int,
    @SerializedName("comments_url")
    val commentsUrl: String,
    @SerializedName("commits_url")
    val commitsUrl: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("files")
    val files: LinkedTreeMap<String, GistsFile>,
    @SerializedName("forks")
    val forks: List<Fork>,
    @SerializedName("forks_url")
    val forksUrl: String,
    @SerializedName("git_pull_url")
    val gitPullUrl: String,
    @SerializedName("git_push_url")
    val gitPushUrl: String,
    @SerializedName("history")
    val history: List<History>,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("public")
    val `public`: Boolean,
    @SerializedName("truncated")
    val truncated: Boolean,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user")
    val user: Any
)

data class Fork(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user")
    val user: User
)

data class User(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("events_url")
    val eventsUrl: String,
    @SerializedName("followers_url")
    val followersUrl: String,
    @SerializedName("following_url")
    val followingUrl: String,
    @SerializedName("gists_url")
    val gistsUrl: String,
    @SerializedName("gravatar_id")
    val gravatarId: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("organizations_url")
    val organizationsUrl: String,
    @SerializedName("received_events_url")
    val receivedEventsUrl: String,
    @SerializedName("repos_url")
    val reposUrl: String,
    @SerializedName("site_admin")
    val siteAdmin: Boolean,
    @SerializedName("starred_url")
    val starredUrl: String,
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)

data class History(
    @SerializedName("change_status")
    val changeStatus: ChangeStatus,
    @SerializedName("committed_at")
    val committedAt: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("version")
    val version: String
)

data class ChangeStatus(
    @SerializedName("additions")
    val additions: Int,
    @SerializedName("deletions")
    val deletions: Int,
    @SerializedName("total")
    val total: Int
)