package com.example.stgoa.gistlib.gist.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap

data class GistsResponse(
    @SerializedName("comments")
    val comments: Int,
    @SerializedName("comments_url")
    val commentsUrl: String,
    @SerializedName("commits_url")
    val commitsUrl: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("files")
    val files: LinkedTreeMap<String, GistsFile>,
    @SerializedName("forks_url")
    val forksUrl: String,
    @SerializedName("git_pull_url")
    val gitPullUrl: String,
    @SerializedName("git_push_url")
    val gitPushUrl: String,
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
    val user: Any?
)

data class GistsFile(
    @SerializedName("filename")
    val filename: String,
    @SerializedName("language")
    val language: String?,
    @SerializedName("raw_url")
    val rawUrl: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("content")
    val content: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(filename)
        parcel.writeString(language)
        parcel.writeString(rawUrl)
        parcel.writeInt(size)
        parcel.writeString(type)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GistsFile> {
        override fun createFromParcel(parcel: Parcel): GistsFile {
            return GistsFile(parcel)
        }

        override fun newArray(size: Int): Array<GistsFile?> {
            return arrayOfNulls(size)
        }
    }
}

data class Owner(
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