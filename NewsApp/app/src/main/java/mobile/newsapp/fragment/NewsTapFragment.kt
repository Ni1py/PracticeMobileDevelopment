package mobile.newsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import mobile.newsapp.adapter.NewsAdapter
import mobile.newsapp.adapter.NewsListsViewPagerAdapter
import mobile.newsapp.data.db.entity.NewsEntity
import mobile.newsapp.databinding.FragmentNewsTapBinding
import mobile.newsapp.viewModel.NewsViewModel

class NewsTapFragment : Fragment() {
    private lateinit var binding: FragmentNewsTapBinding
    private lateinit var adapter: NewsListsViewPagerAdapter
    private val newsViewModel: NewsViewModel by activityViewModels()
    private val fragList = listOf<Fragment>(
        NewsVisibleListFragment.newInstance(),
        NewsHiddenListFragment.newInstance()
    )
    private val fragListTitles = listOf(
        "Видимые",
        "Скрытые"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsTapBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NewsListsViewPagerAdapter(requireActivity(), fragList)
        binding.vpNewsList.adapter = adapter
        TabLayoutMediator(binding.newsListsTab, binding.vpNewsList) {
            tab, pos -> tab.text = fragListTitles[pos]
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsTapFragment()
    }
}
