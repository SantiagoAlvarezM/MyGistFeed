package com.example.stgoa.mygistfeed.common

import android.os.Parcel
import com.example.stgoa.gistlib.gist.service.model.GistResponse
import com.example.stgoa.gistlib.gist.service.model.GistsFile
import com.example.stgoa.gistlib.gist.service.model.GistsResponse
import com.example.stgoa.mygistfeed.detail.model.GistItem
import com.example.stgoa.mygistfeed.home.model.GistsListItem
import com.google.gson.internal.LinkedTreeMap

fun GistsResponse.toListItem(): GistsListItem {
    return GistsListItem(
        id,
        comments,
        commentsUrl,
        description.orEmpty(),
        forksUrl,
        updatedAt,
        createdAt,
        url,
        owner.avatarUrl,
        owner.login,
        files.size,
        files,
        0
    )
}

fun GistResponse.toItem(): GistItem {
    return GistItem(
        owner.avatarUrl,
        files,
        owner.login,
        updatedAt,
        createdAt
    )
}

fun Parcel.readMap(map: LinkedTreeMap<String, GistsFile>): LinkedTreeMap<String, GistsFile> {
    val temp = LinkedTreeMap<String, GistsFile>()
    readMap(temp, map.javaClass.classLoader)
    temp.forEach {
        map[it.key] = it.value
    }
    return map
}