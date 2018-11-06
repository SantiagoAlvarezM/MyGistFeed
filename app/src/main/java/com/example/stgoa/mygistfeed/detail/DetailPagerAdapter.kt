package com.example.stgoa.mygistfeed.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.stgoa.gistlib.gist.service.model.GistsFile
import com.google.gson.internal.LinkedTreeMap

class DetailPagerAdapter(
    fragmentManager: FragmentManager,
    private val files: LinkedTreeMap<String, GistsFile>
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return DetailItemFragment.newInstance(files[files.keys.elementAt(position)])
    }

    override fun getCount(): Int {
        return files.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return files.keys.elementAt(0).orEmpty()
    }

    fun updateData(data: LinkedTreeMap<String, GistsFile>) {
        files.clear()
        files.putAll(data)
        notifyDataSetChanged()
    }
}