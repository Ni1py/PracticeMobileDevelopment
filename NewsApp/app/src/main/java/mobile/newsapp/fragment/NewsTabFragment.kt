package mobile.newsapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import mobile.newsapp.R
import mobile.newsapp.adapter.NewsListsViewPagerAdapter
import mobile.newsapp.databinding.FragmentNewsTabBinding
import mobile.newsapp.viewModel.NewsViewModel

class NewsTabFragment : Fragment() {
    private lateinit var binding: FragmentNewsTabBinding
    private val newsViewModel: NewsViewModel by activityViewModels()
    private val fragList = listOf<Fragment>(
        NewsListFragment.newInstance(false),
        NewsListFragment.newInstance(true)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsTabBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.vpNewsList.adapter = NewsListsViewPagerAdapter(requireActivity(), fragList)
        initTabLayoutMediator()
        selectPage()
        initTabListener()
    }

    private fun initTabLayoutMediator() {
        TabLayoutMediator(binding.newsListsTab, binding.vpNewsList) { tab, pos ->
            tab.text = resources.getStringArray(R.array.fragListTitles)[pos].uppercase()
        }.attach()
    }

    private fun selectPage() {
        newsViewModel.currentTabPos.observe(requireActivity()) { pos ->
            binding.vpNewsList.apply { doOnPreDraw { currentItem = pos } }
        }
    }

    private fun initTabListener() {
        binding.newsListsTab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                newsViewModel.currentTabPos.value = tab?.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsTabFragment()
    }
}
