package com.example.stgoa.mygistfeed.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.stgoa.mygistfeed.R
import com.example.stgoa.mygistfeed.databinding.GistItemBinding
import com.example.stgoa.mygistfeed.home.model.GistsListItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class GistAdapter(
    private val gists: MutableList<GistsListItem>
) : RecyclerView.Adapter<GistAdapter.ViewHolder>() {

    private val actionsSubject = PublishSubject.create<Pair<Int, GistsListItem>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.gist_item, parent, false))
    }

    override fun getItemCount(): Int {
        return gists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gists[position])
    }

    fun updateData(items: List<GistsListItem>) {
        gists.clear()
        gists.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: GistsListItem) {
            val binding = DataBindingUtil.bind<GistItemBinding>(view)
            binding?.let {
                it.item = item
                it.executePendingBindings()
            }
            itemView.setOnClickListener { actionsSubject.onNext(adapterPosition to gists[adapterPosition]) }
        }
    }

    fun observableActions(): Observable<Pair<Int, GistsListItem>> = actionsSubject.hide()
}