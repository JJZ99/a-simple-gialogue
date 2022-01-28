package com.example.abcdialogue.Weibo

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.abcdialogue.MyApplication
import com.example.abcdialogue.Weibo.Adapter.MyViewPageAdapter
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Model.WBViewModel
import com.example.abcdialogue.Weibo.Model.WBViewModelFactory
import com.example.abcdialogue.Weibo.Prestener.IPresenter
import com.example.abcdialogue.Weibo.Prestener.IPresenter2
import com.example.abcdialogue.Weibo.View.Fragment.FragmentFactory
import kotlinx.android.synthetic.main.activity_wei_bo.setting_more
import kotlinx.android.synthetic.main.activity_wei_bo.tablayout_button
import kotlinx.android.synthetic.main.activity_wei_bo.viewPager

class WeiBoActivity : AppCompatActivity() {
    var count = 1
     val TAG = "WeiBoActivity"

    private val viewModel by lazy{
        ViewModelProvider(this, WBViewModelFactory()).get(WBViewModel::class.java)
    }
    private var mTitles: MutableList<String>? = mutableListOf()
    private var mFragments: MutableList<Fragment>? = mutableListOf()
    private var mPresenter = IPresenter(this)

    private var mPresenter2 = IPresenter2(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //去掉title
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_wei_bo)
        lifecycle.addObserver(mPresenter)
        lifecycle.addObserver(mPresenter2)

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

        setting_more.setOnClickListener {
            var intent = Intent(this, FontSizeActivity().javaClass)
            startActivity(intent)
        }
//        Log.i(TAG,this.toString())
//        Log.i(TAG,this.baseContext.toString())
//        Log.i(TAG,this.application.toString())
//        Log.i(TAG,this.applicationContext.toString())

//        Log.i("StartActivity",this.resources.toString())
        Log.i(TAG + MyApplication.CON, "onCreate"+this.window.toString())

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
        viewPager.currentItem = 1
    }

    private fun initData() {
        Log.i(TAG,"=======into initData=======")

        mTitles?.add("视频")
        mTitles?.add("推荐")
        mTitles?.add("商品")
        mFragments?.add(FragmentFactory.getInstance(2))
        mFragments?.add(FragmentFactory.getInstance(1))
        mFragments?.add(FragmentFactory.getInstance(3))

    }
    override fun getResources(): Resources {
        val res: Resources = super.getResources()
        if (count == 1) {
            Log.i(TAG + MyApplication.CON, this.baseContext.toString())

            Log.i(TAG + MyApplication.CON, this.toString())
            --count
        }
        return res
    }

    override fun onDestroy() {
        super.onDestroy()
        mTitles?.clear()
        mFragments?.clear()
        lifecycle.removeObserver(mPresenter)
        lifecycle.removeObserver(mPresenter2)

    }
}