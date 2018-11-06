package com.example.stgoa.mygistfeed.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.stgoa.gistlib.common.Resource
import com.example.stgoa.gistlib.common.Status
import com.example.stgoa.gistlib.gist.service.model.GistsFile
import com.example.stgoa.mygistfeed.R
import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme

object GistsBindings {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView?, path: String?) {
        if (imageView != null) {
            Glide.with(imageView)
                .load(path)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_mark)
                        .error(R.drawable.ic_mark)
                )
                .into(imageView)
        }
    }

    @BindingAdapter("setRefreshing")
    @JvmStatic
    fun setRefreshing(refreshLayout: SwipeRefreshLayout?, resource: Resource<*>?) {
        if (refreshLayout == null || resource == null) return
        with(refreshLayout) {
            isRefreshing = resource.status == Status.LOADING
        }
    }

    @BindingAdapter("code")
    @JvmStatic
    fun setCode(codeView: CodeView?, resource: Resource<*>?) {
        if (codeView == null || resource == null) return
        resource.data?.run {
            val file = this as GistsFile
            codeView.setOptions(
                Options.get(codeView.context)
                    .withLanguage(file.language.orEmpty())
                    .withCode(file.content.orEmpty())
                    .withTheme(ColorTheme.DEFAULT)
            )
        }
    }
}