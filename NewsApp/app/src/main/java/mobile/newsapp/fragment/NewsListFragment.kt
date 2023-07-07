package mobile.newsapp.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import mobile.newsapp.adapter.NewsRecyclerViewAdapter
import mobile.newsapp.data.db.entity.NewsEntity
import mobile.newsapp.databinding.FragmentNewsListBinding
import mobile.newsapp.extension.withPercentages
import mobile.newsapp.viewModel.NewsViewModel
import java.time.LocalDate
import java.util.Collections.sort

private const val IS_HIDDEN = "isHidden"

class NewsListFragment : Fragment(), NewsRecyclerViewAdapter.Listener {

    private lateinit var binding: FragmentNewsListBinding
    private lateinit var adapter: NewsRecyclerViewAdapter
    private val newsViewModel: NewsViewModel by activityViewModels()
    private var isHidden = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getBoolean(IS_HIDDEN)?.let {
            isHidden = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsListBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NewsRecyclerViewAdapter(this, this)
        adapter.apply {
            if (isHidden)
                newsViewModel.newsHiddenList.observe(requireActivity())
                { list ->
                    sortByDate(list)
                    submitList(list) }
            else
                newsViewModel.newsVisibleList.observe(requireActivity())
                { list ->
                    sortByDate(list)
                    submitList(list) }
        }
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(context)
            rcView.adapter = adapter

            svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean { return true }
                override fun onQueryTextChange(text: String?): Boolean {
                    if (isHidden) newsViewModel.searchHiddenWord.value = text?.withPercentages()
                    else newsViewModel.searchVisibleWord.value = text?.withPercentages()
                    return true
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sortByDate (list: List<NewsEntity>) =
        sort(list) { element1, element2 ->
            LocalDate.parse(element2.news_date).compareTo(LocalDate.parse(element1.news_date))
        }

    companion object {
        @JvmStatic
        fun newInstance(isHidden: Boolean) = NewsListFragment().apply {
            arguments = Bundle().apply {
                putBoolean(IS_HIDDEN, isHidden)
            }
        }
    }

    override fun onCLickCard(news: NewsEntity) {
        newsViewModel.currentNews.value = news
    }

    override fun onClickHiddenButton(news: NewsEntity) {
        newsViewModel.hiddenNews.value = news
    }
}