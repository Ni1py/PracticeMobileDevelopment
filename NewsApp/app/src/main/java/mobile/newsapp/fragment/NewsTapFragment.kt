package mobile.newsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import mobile.newsapp.adapter.NewsListsViewPagerAdapter
import mobile.newsapp.databinding.FragmentNewsTapBinding

class NewsTapFragment : Fragment() {
    private lateinit var binding: FragmentNewsTapBinding
    private lateinit var adapter: NewsListsViewPagerAdapter
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
    ): View {
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
