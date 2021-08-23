package com.example.abcdialogue.Weibo.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyViewPageAdapter(fragmentManager: FragmentManager,
                        private val context: Context,
                        private val fragments: MutableList<Fragment>,
                        private val titles: List<String>) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun getItem(p0: Int): Fragment {
        return fragments[p0]
    }

    /**
     * 显示tab上的名字
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}