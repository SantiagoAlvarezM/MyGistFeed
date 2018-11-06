package com.example.stgoa.mygistfeed.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.stgoa.gistlib.common.Resource
import com.example.stgoa.gistlib.gist.service.model.GistsFile
import com.example.stgoa.mygistfeed.R
import com.example.stgoa.mygistfeed.databinding.FragmentDetailItemBinding

class DetailItemFragment : Fragment() {

    private var resource: Resource<*> = Resource.loading(null)

    private lateinit var dataBinding: FragmentDetailItemBinding

    companion object {

        private const val ARG_GIST_FILE = "gist_file"

        @JvmStatic
        fun newInstance(gistsFile: GistsFile?): DetailItemFragment {
            return gistsFile?.let {
                DetailItemFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_GIST_FILE, gistsFile)
                    }
                }
            } ?: DetailItemFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resource = arguments?.run {
            Resource.success(getParcelable(ARG_GIST_FILE) as GistsFile)
        } ?: Resource.error(getString(R.string.general_error), null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_item, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.resource = resource
    }
}
