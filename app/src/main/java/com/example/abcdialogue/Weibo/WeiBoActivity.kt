package com.example.abcdialogue.Weibo

import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.abcdialogue.Weibo.Adapter.MyViewPageAdapter
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Model.WBViewModel
import com.example.abcdialogue.Weibo.Model.WBViewModelFactory
import com.example.abcdialogue.Weibo.View.Fragment.FragmentFactory
import kotlinx.android.synthetic.main.activity_wei_bo.tablayout_button
import kotlinx.android.synthetic.main.activity_wei_bo.viewPager

class WeiBoActivity : AppCompatActivity() {
    private val viewModel by lazy{
        ViewModelProvider(this, WBViewModelFactory()).get(WBViewModel::class.java)
    }
    private var mTitles: MutableList<String>? = mutableListOf()
    private var mFragments: MutableList<Fragment>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //去掉title
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_wei_bo)

        initData()
        initView()
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            //如果页面滑动了
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.i("PagerListener", " scrolled $position")
            }
            //显示当前页面的位置
            override fun onPageSelected(position: Int) {
                // Toast.makeText(this,"PageSelected $position",Toast.LENGTH_SHORT).show()
                Log.i("PagerListener", " PageSelected $position")
            }


            //页面滑动状态的改变，0表示空闲稳定的状态，1表示正在被拖动 2表示定位到最终位置
            override fun onPageScrollStateChanged(state: Int) {
                Log.i("PagerListener", "ScrollState $state")
            }
        })
    }
    private fun initView() {
        Log.i(TAG,"=======into initView=======")
        viewPager.adapter = MyViewPageAdapter(
            supportFragmentManager,
            this,
            mFragments!!,
            mTitles!!
        )
        tablayout_button.setupWithViewPager(viewPager)
    }

    private fun initData() {
        Log.i(TAG,"=======into initData=======")

        mTitles?.add("新闻")
        mTitles?.add("视频")
        mTitles?.add("商品")
        mFragments?.add(FragmentFactory.getInstance(1))
        mFragments?.add(FragmentFactory.getInstance(2))
        mFragments?.add(FragmentFactory.getInstance(3))

    }

    override fun onDestroy() {
        super.onDestroy()
        mTitles?.clear()
        mFragments?.clear()
    }

    companion object {
        const val TAG = "========WeiBoActivity==========="
    }
}