package com.example.stgoa.mygistfeed.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stgoa.gistlib.auth.services.GithubConfig
import com.example.stgoa.gistlib.common.Resource
import com.example.stgoa.gistlib.common.Status
import com.example.stgoa.gistlib.common.disposeSecure
import com.example.stgoa.gistlib.gist.service.model.ApiError
import com.example.stgoa.mygistfeed.R
import com.example.stgoa.mygistfeed.common.errorToast
import com.example.stgoa.mygistfeed.databinding.FragmentGistsBinding
import com.example.stgoa.mygistfeed.home.model.GistsListItem
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class GistsFragment @Inject constructor() : DaggerFragment() {

    companion object {
        const val ARG_FEED_TYPE = "feed_type"

        fun newInstance(feedType: FeedType): GistsFragment {
            return GistsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_FEED_TYPE, feedType)
                }
            }
        }
    }

    private lateinit var viewModel: GistsViewModel
    private lateinit var dataBinding: FragmentGistsBinding

    private var manageViewDisposable: Disposable? = null
    private var actionsDisposable = CompositeDisposable()

    private val gistAdapter = GistAdapter(mutableListOf())
    private lateinit var currentFeedType: FeedType
    private lateinit var userInteractionListener: UserInteractionListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        check(context is UserInteractionListener) { "Activity must implement userInteractionListener" }
        userInteractionListener = context as UserInteractionListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVars()
        startObserving()
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_gists, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    override fun onDestroy() {
        manageViewDisposable?.disposeSecure()
        actionsDisposable.disposeSecure()
        super.onDestroy()
    }

    private fun initVars() {
        currentFeedType = arguments?.run {
            this.getSerializable(ARG_FEED_TYPE) as FeedType
        } ?: FeedType.PUBLIC
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(GistsViewModel::class.java)
        } ?: throw IllegalStateException("Invalid Activity")
    }

    private fun initViews() {
        with(dataBinding.recyclerViewGists) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            this.adapter = gistAdapter
        }
    }

    private fun initListeners() {
        dataBinding.swipeRefreshGists.setOnRefreshListener { viewModel.loadData(currentFeedType) }
        actionsDisposable.add(gistAdapter.observableActions()
            .subscribe { userInteractionListener.onListItemClick(it.first, it.second) })
    }

    private fun initData() {
        viewModel.loadData(currentFeedType)
    }

    private fun startObserving() {
        manageViewDisposable = viewModel.observableViewModelActions()
            .subscribe({ resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        gistAdapter.updateData(resources.data as List<GistsListItem>)
                        dataBinding.resource = resources
                    }
                    Status.ERROR -> {
                        val errorCode = (resources.data as ApiError).code
                        val message =
                            if (errorCode == GithubConfig.ERROR_NOTFOUND ||
                                errorCode == GithubConfig.ERROR_FORBIDDEN ||
                                errorCode == GithubConfig.ERROR_UNAUTHORIZED) {
                                getString(R.string.error_401)
                            } else {
                                getString(R.string.general_error)
                            }

                        dataBinding.resource = Resource.error(message, null)
                    }
                    else -> {
                        dataBinding.resource = resources
                    }
                }
            }, { errorToast(R.string.general_error) })
    }
}
