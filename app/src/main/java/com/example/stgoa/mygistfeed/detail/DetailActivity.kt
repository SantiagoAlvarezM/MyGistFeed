package com.example.stgoa.mygistfeed.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.stgoa.gistlib.common.Status
import com.example.stgoa.gistlib.common.disposeSecure
import com.example.stgoa.mygistfeed.R
import com.example.stgoa.mygistfeed.detail.model.GistItem
import com.example.stgoa.mygistfeed.home.GistsViewModel
import com.example.stgoa.mygistfeed.home.model.GistsListItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.gson.internal.LinkedTreeMap
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    companion object {

        private const val ARG_GIST_FILE = "gist_file"

        @JvmStatic
        fun createIntent(context: Context, gistsListItem: GistsListItem): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(ARG_GIST_FILE, gistsListItem)
            }
        }
    }

    private lateinit var gistsListItem: GistsListItem

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: GistsViewModel

    private var manageViewDisposable: Disposable? = null

    private var pagerAdapter: DetailPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        gistsListItem = intent.getParcelableExtra(ARG_GIST_FILE)
        supportActionBar?.title = gistsListItem.username

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GistsViewModel::class.java)
        viewModel.loadGist(gistsListItem.id)

        initViews()
        initListeners()
        startObserving()
    }

    override fun onDestroy() {
        manageViewDisposable?.disposeSecure()
        super.onDestroy()
    }

    private fun initViews() {
        pagerAdapter = DetailPagerAdapter(supportFragmentManager, LinkedTreeMap())
        viewPagerFiles.adapter = pagerAdapter
        tabLayoutFiles.setupWithViewPager(viewPagerFiles)
    }

    private fun initListeners() {
        viewPagerFiles.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
            }
        })
        viewPagerFiles.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutFiles))
        tabLayoutFiles.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPagerFiles))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun startObserving() {
        manageViewDisposable = viewModel.observableViewModelActions()
            .subscribe({ resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        pagerAdapter?.updateData((resources.data as GistItem).files)
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                    }
                }
            }, {
                Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show()
            })
    }
}
