package mobile.newsapp.adapter

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mobile.newsapp.R
import mobile.newsapp.data.db.entity.NewsEntity
import mobile.newsapp.data.model.NewsModel
import mobile.newsapp.databinding.NewsItemBinding

class NewsAdapter(private val listener: Listener) : ListAdapter<NewsEntity, NewsAdapter.Holder>(Comparator()) {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NewsItemBinding.bind(view)

        fun bind (news: NewsEntity, listener: Listener) = with(binding) {
            tvTitle.text = news.title
            tvAnnotation.text = news.annotation
            cvItem.setOnClickListener {
                listener.onCLickCard(news)
            }
            swHidden.setOnClickListener {
                listener.onClickHiddenButton(news)
            }
            swHidden.isChecked = news.hidden
            ivVisibility.setImageResource(if (swHidden.isChecked) R.drawable.ic_invisible else R.drawable.ic_visible)
        }
    }

    class Comparator : DiffUtil.ItemCallback<NewsEntity>() {
        override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    interface Listener {
        fun onCLickCard(news: NewsEntity)
        fun onClickHiddenButton(news: NewsEntity)
    }
}