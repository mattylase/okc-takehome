package com.mattylase.okctakehome

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mattylase.okctakehome.ui.common.TakehomeViewModel
import com.mattylase.okctakehome.ui.main.MatchFragment
import com.mattylase.okctakehome.ui.main.SpecialBlendFragment
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityRetainedScope
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class MainActivity : FragmentActivity(), AndroidScopeComponent {

    override val scope: Scope by activityRetainedScope()
    val viewModel by viewModel<TakehomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabs = findViewById<TabLayout>(R.id.tabLayout)

        findViewById<ViewPager2>(R.id.viewPager).apply {
            adapter = PagesAdapter(this@MainActivity)
            TabLayoutMediator(tabs, this) { tab, pos ->
                when(pos) {
                    0 -> tab.text = getString(R.string.tab_label_special_blend)
                    1 -> tab.text = getString(R.string.tab_label_match_percent)
                }
            }.attach()
        }
    }
}

class PagesAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SpecialBlendFragment()
            1 -> MatchFragment()
            else -> throw IllegalStateException("We only support 2 pages!")
        }
    }

}