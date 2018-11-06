package com.example.stgoa.mygistfeed.home.model

import android.os.Parcel
import android.os.Parcelable
import com.example.stgoa.gistlib.gist.service.model.GistsFile
import com.example.stgoa.mygistfeed.common.readMap
import com.google.gson.internal.LinkedTreeMap

data class GistsListItem(
    val id: String,
    val comments: Int,
    val commentsUrl: String,
    val description: String,
    val forksUrl: String,
    val updatedAt: String,
    val createdAt: String,
    val url: String,
    val avatarUrl: String,
    val username: String,
    val filesCount: Int,
    val files: LinkedTreeMap<String, GistsFile> = LinkedTreeMap(),
    val forks: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readMap(LinkedTreeMap()),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(comments)
        parcel.writeString(commentsUrl)
        parcel.writeString(description)
        parcel.writeString(forksUrl)
        parcel.writeString(updatedAt)
        parcel.writeString(createdAt)
        parcel.writeString(url)
        parcel.writeString(avatarUrl)
        parcel.writeString(username)
        parcel.writeInt(filesCount)
        parcel.writeMap(files)
        parcel.writeInt(forks)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GistsListItem> {
        override fun createFromParcel(parcel: Parcel): GistsListItem {
            return GistsListItem(parcel)
        }

        override fun newArray(size: Int): Array<GistsListItem?> {
            return arrayOfNulls(size)
        }
    }
}