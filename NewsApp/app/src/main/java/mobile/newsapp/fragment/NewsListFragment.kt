package mobile.newsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnCloseListener
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import mobile.newsapp.adapter.NewsAdapter
import mobile.newsapp.data.model.NewsModel
import mobile.newsapp.databinding.FragmentNewsListBinding
import mobile.newsapp.viewModel.NewsViewModel

class NewsListFragment : Fragment(), NewsAdapter.Listener {
    private lateinit var binding: FragmentNewsListBinding
    private lateinit var adapter: NewsAdapter
    private val newsViewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NewsAdapter(this)
        newsViewModel.newsList.observe(activity as LifecycleOwner) {
            adapter.submitList(it)
        }
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(context)
            rcView.adapter = adapter
            svNews.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    newsViewModel.searchWord.value = "%$text%"
                    return true
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsListFragment()
    }

    override fun onCLick(news: NewsModel) {
        newsViewModel.isClick.value = true
        newsViewModel.currentNews.value = news
    }
}
