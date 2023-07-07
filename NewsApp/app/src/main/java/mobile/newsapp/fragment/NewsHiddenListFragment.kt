package mobile.newsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import mobile.newsapp.adapter.NewsRecyclerViewAdapter
import mobile.newsapp.data.db.entity.NewsEntity
import mobile.newsapp.databinding.FragmentNewsHiddenListBinding
import mobile.newsapp.viewModel.NewsViewModel

class NewsHiddenListFragment : Fragment(), NewsRecyclerViewAdapter.Listener {
    private lateinit var binding: FragmentNewsHiddenListBinding
    private lateinit var adapter: NewsRecyclerViewAdapter
    private val newsViewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsHiddenListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NewsRecyclerViewAdapter(this, this)
        newsViewModel.newsHiddenList.observe(requireActivity())
        { list -> adapter.submitList(list) }
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(context)
            rcView.adapter = adapter

            svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean { return true }
                override fun onQueryTextChange(text: String?): Boolean {
                    newsViewModel.searchHiddenWord.value = "%$text%"
                    return true
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsHiddenListFragment()
    }

    override fun onCLickCard(news: NewsEntity) {
        newsViewModel.currentNews.value = news
    }

    override fun onClickHiddenButton(news: NewsEntity) {
        newsViewModel.hiddenNews.value = news
    }
}