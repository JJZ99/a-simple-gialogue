package com.example.abcdialogue.Weibo.View.Fragment

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.Module.MainActivity2
import com.example.abcdialogue.MyApplication
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder
import kotlinx.android.synthetic.main.fragment_liner_recycler.add_btn
import kotlinx.android.synthetic.main.fragment_liner_recycler.new_rv
import kotlinx.android.synthetic.main.fragment_liner_recycler.refresh_layout
import kotlinx.android.synthetic.main.fragment_liner_recycler.remove_btn
import androidx.lifecycle.ViewModelProvider
import com.example.abcdialogue.Util.Util.toastInfo
import com.example.abcdialogue.Weibo.Adapter.LoadStatus
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter.Companion.PAGESIZE
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter.Companion.currStatus
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter.Companion.page
import com.example.abcdialogue.Weibo.InitSDK.Companion.TOKEN
import com.example.abcdialogue.Weibo.Model.WBViewModelFactory
import com.example.abcdialogue.Weibo.Model.WBViewModel


class NewFragment: Fragment(R.layout.fragment_liner_recycler) {

    private val viewModel by lazy{
        ViewModelProvider(this, WBViewModelFactory()).get(WBViewModel::class.java)
    }
    lateinit var adapter: MyRecyclerAdapter

    var isRefresh = false

    override fun onCreate(savedInstanceState: Bundle?) {
        //初始化数据
        initData()
        Log.i("onCreateFragment","=============onCreateFragment=============")

        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.statusList.observe(this.viewLifecycleOwner,{
            if (isRefresh||page==2){
                initRecycler()
                refresh_layout.isRefreshing = false
                isRefresh=false
            }
        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("onViewCreated","==================onViewCreatedonViewCreated========================")
        adapter = MyRecyclerAdapter(this,viewModel)
        initListener()
        initRecycler()
        handlerDownPullUpdate()
    }

    private fun initData() {
        viewModel.getStatusesList(TOKEN,page++)
    }

    private fun initRecycler() {
        new_rv.layoutManager = LinearLayoutManager(this.context)
        new_rv.adapter = adapter
        Log.i("进initRecycler","initRecyclerinitRecyclerinitRecycler")
    }

    private fun initListener(){

        adapter.onItemClickListener = object : MyRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(view: View,pos:Int) {
                "你点击了第${pos}Item".toastInfo()
                var intent = Intent(MyApplication.context, MainActivity2().javaClass)
                startActivity(intent)
            }
        }
        adapter.onLoadMoreListener = object : MyRecyclerAdapter.OnLoadMoreListener {
            override fun onLoadMore(view: MyFooterViewHolder) {
                "你调用了onLoadMore".toastInfo()
                currStatus = LoadStatus.LoadMoreIn
                viewModel.getStatusesList(TOKEN, page++)
                viewModel.statusList.observe(this@NewFragment.viewLifecycleOwner,{
                    var hasNumber = (page-2)* PAGESIZE
                    var total = it.size
                    for (i in hasNumber until total){
                        adapter.addItem(adapter.itemCount-1,it[i])
                        Log.i("loadmore observe",it[i].toString())
                    }
                })
            }
        }

        add_btn.setOnClickListener{
            "已经退出历史舞台,不用再改了".toastInfo()
        }

        remove_btn.setOnClickListener{
            "已经退出历史舞台，不用再改了".toastInfo()
        }

        refresh_layout.setOnRefreshListener {
            viewModel.statusList.value?.clear()
            isRefresh = true
            page = 1
            viewModel.getStatusesList(TOKEN, page++)

        }
    }

    private fun handlerDownPullUpdate(){
        //设置可用
        refresh_layout.isEnabled = true
        //加载动画三种颜色轮训
        refresh_layout.setColorSchemeColors(0xff0000,0x00ff00,0x0000ff)
        //refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        //refresh_layout.setProgressBackgroundColorSchemeColor(0x03DAC5);
    }

    override fun onPause() {
        super.onPause()
    }
    override fun onDestroy() {
        Log.i("onDestroy","=====dasdasda==========")
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }
}