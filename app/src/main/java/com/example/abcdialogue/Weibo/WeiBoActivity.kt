package com.example.abcdialogue.Weibo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.abcdialogue.Adapter.MyViewPageAdapter
import com.example.abcdialogue.Module.Fragment.GoodFragment
import com.example.abcdialogue.Module.Fragment.NewFragment
import com.example.abcdialogue.Module.Fragment.VideoFragment
import com.example.abcdialogue.R
import kotlinx.android.synthetic.main.activity_wei_bo.tablayout_button
import kotlinx.android.synthetic.main.activity_wei_bo.tablayout_top
import kotlinx.android.synthetic.main.activity_wei_bo.viewPager

class WeiBoActivity : AppCompatActivity() {

    private var mTitles: MutableList<String>? = mutableListOf()
    private var mFragments: MutableList<Fragment>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        tablayout_top.setupWithViewPager(viewPager)
    }

    private fun initData() {
        Log.i(TAG,"=======into initData=======")

        this.intent.getStringExtra("token")?.let { mTitles?.add(it) }
        mTitles?.add("视频")
        mTitles?.add("商品")
        mFragments?.add(NewFragment())
        mFragments?.add(VideoFragment())
        mFragments?.add(GoodFragment())
    }

    companion object {
        const val TAG = "========WeiBoActivity==========="
    }
}