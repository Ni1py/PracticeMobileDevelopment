package mobile.newsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import mobile.newsapp.R
import mobile.newsapp.adapter.NewsListsViewPagerAdapter
import mobile.newsapp.databinding.FragmentNewsTabBinding

class NewsTabFragment : Fragment() {
    private lateinit var binding: FragmentNewsTabBinding
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
        TabLayoutMediator(binding.newsListsTab, binding.vpNewsList) { tab, pos ->
            tab.text = resources.getStringArray(R.array.fragListTitles)[pos].uppercase()
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsTabFragment()
    }
}
