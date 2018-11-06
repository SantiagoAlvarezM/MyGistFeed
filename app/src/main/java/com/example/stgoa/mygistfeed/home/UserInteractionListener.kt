package com.example.stgoa.mygistfeed.home

import com.example.stgoa.mygistfeed.home.model.GistsListItem

interface UserInteractionListener {

    fun onListItemClick(position: Int, gistsListItem: GistsListItem)
}