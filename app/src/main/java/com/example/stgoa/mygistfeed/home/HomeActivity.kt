package com.example.stgoa.mygistfeed.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.stgoa.gistlib.auth.login.LoginActivity
import com.example.stgoa.gistlib.common.disposeSecure
import com.example.stgoa.mygistfeed.R
import com.example.stgoa.mygistfeed.detail.DetailActivity
import com.example.stgoa.mygistfeed.home.model.GistsListItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), UserInteractionListener {

    private val SELECTED_POSITION_KEY = "selected_position"
    private var selectedPosition: Int = RecyclerView.NO_POSITION
    private val CURRENT_FEED_TYPE_KEY = "current_feed_type"
    private var currentFeedType: FeedType = FeedType.PUBLIC

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: GistsViewModel

    private val uiDisposable = CompositeDisposable()

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        initVars(savedInstanceState)
        initViews()
        initListeners()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        viewPagerHome.currentItem = currentFeedType.ordinal
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let {
            it.putInt(SELECTED_POSITION_KEY, selectedPosition)
            it.putSerializable(CURRENT_FEED_TYPE_KEY, currentFeedType)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        uiDisposable.disposeSecure()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_login)?.run {
            this.title = if (viewModel.getUser().authenticated) {
                getString(R.string.title_logout)
            } else {
                getString(R.string.title_sigin)
            }
            return true
        } ?: return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_login -> {
                if (viewModel.getUser().authenticated) {
                    viewModel.getUser().clear()
                    //TODO delete user in API refactor this
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initVars(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            selectedPosition = savedInstanceState.getInt(SELECTED_POSITION_KEY)
            currentFeedType = savedInstanceState.getSerializable(CURRENT_FEED_TYPE_KEY) as FeedType
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GistsViewModel::class.java)
    }

    private fun initViews() {
        mSectionsPagerAdapter =
                SectionsPagerAdapter(supportFragmentManager, resources.getStringArray(R.array.home_tabs))
        viewPagerHome.adapter = mSectionsPagerAdapter
        tabLayoutHome.setupWithViewPager(viewPagerHome)
    }

    private fun initListeners() {
        viewPagerHome.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentFeedType = FeedType.values()[position]
                viewModel.loadData(currentFeedType)
            }
        })
        viewPagerHome.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutHome))
        tabLayoutHome.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPagerHome))
        fabHome.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onListItemClick(position: Int, gistsListItem: GistsListItem) {
        selectedPosition = position
        goToGistsDetail(gistsListItem)
    }

    private fun goToGistsDetail(gistsListItem: GistsListItem) {
        startActivity(DetailActivity.createIntent(this, gistsListItem))
    }

    inner class SectionsPagerAdapter(fm: FragmentManager, private val titles: Array<String>) :
        FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return GistsFragment.newInstance(currentFeedType)
        }

        override fun getCount(): Int {
            return titles.count()
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}
