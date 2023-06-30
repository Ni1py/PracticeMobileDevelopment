package mobile.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mobile.newsapp.data.model.News
import mobile.newsapp.databinding.NewsItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    private val newsList = ArrayList<News>()

    class NewsHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = NewsItemBinding.bind(item)
        fun bind(news: News) = with(binding) {
            tvTitle.text = news.title
            tvAnnotation.text = news.annotation
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(newsList[position])
        notifyDataSetChanged()
    }

    fun setNewsList(list: List<News>) {
        newsList.addAll(list)
    }
}