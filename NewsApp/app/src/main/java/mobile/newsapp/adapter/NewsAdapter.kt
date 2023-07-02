package mobile.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mobile.newsapp.R
import mobile.newsapp.data.model.NewsModel
import mobile.newsapp.databinding.NewsItemBinding

class NewsAdapter(private val listener: Listener) : ListAdapter<NewsModel, NewsAdapter.Holder>(Comparator()) {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NewsItemBinding.bind(view)

        fun bind (news: NewsModel, listener: Listener) = with(binding) {
            tvTitle.text = news.title
            tvAnnotation.text = news.annotation
            cvItem.setOnClickListener {
                listener.onCLick(news)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<NewsModel>() {
        override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
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
        fun onCLick(news: NewsModel)
    }
}