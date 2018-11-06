package com.example.stgoa.mygistfeed.detail.model

import com.example.stgoa.gistlib.gist.service.model.GistsFile
import com.google.gson.internal.LinkedTreeMap

data class GistItem(
    val avatarUrl: String,
    val files: LinkedTreeMap<String, GistsFile>,
    val username: String,
    val updatedAt: String,
    val createdAt: String
)
