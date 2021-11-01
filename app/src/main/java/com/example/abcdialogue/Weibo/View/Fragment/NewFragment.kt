package com.example.abcdialogue.Weibo.View.Fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder
import kotlinx.android.synthetic.main.fragment_liner_recycler.new_rv
import kotlinx.android.synthetic.main.fragment_liner_recycler.refresh_layout
import androidx.lifecycle.ViewModelProvider
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastSuccess
import com.example.abcdialogue.Weibo.state.LoadStatus
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter.Companion.PAGESIZE
import com.example.abcdialogue.Weibo.Bean.WBStatusBean
import com.example.abcdialogue.Weibo.InitSDK.Companion.TOKEN
import com.example.abcdialogue.Weibo.Model.WBViewModelFactory
import com.example.abcdialogue.Weibo.Model.WBViewModel
import com.example.abcdialogue.Weibo.Util.FrescoUtil
import com.example.abcdialogue.Weibo.Util.ParseUtil.getLarge2MiddleUrl
import kotlinx.android.synthetic.main.fragment_liner_recycler.fab


class NewFragment: Fragment(R.layout.fragment_liner_recycler) {


    private val viewModel by lazy{
        ViewModelProvider(this, WBViewModelFactory()).get(WBViewModel::class.java)
    }
    lateinit var adapter: MyRecyclerAdapter
    private var observerPageIs2 = Observer<MutableList<WBStatusBean>> {
        //这里做两层判断是要把打开app第一次请求和刷新区分开来，
        if (viewModel.page == 2) {
            //如果是刷新操作
            if (isRefresh) {
                "刷新成功".toastSuccess()
                //通知任何已注册的观察者数据集已更改，刷新 recyclerview
                adapter.notifyDataSetChanged()
                //定位到第一项
                new_rv.scrollToPosition(0)
                refresh_layout.isRefreshing = false
                isRefresh = false
            } else {
                //如果是第一次请求就初始化 recyclerview
                initRecycler()
            }
        }
    }
    private var observerRefreshError = Observer<LoadStatus> {
        if (isRefresh && it == LoadStatus.LoadMoreError) {
            refresh_layout.isRefreshing = false
            // "下拉刷新失败，请检查网络".toastError()
        }
    }
    private var observerLoadMore = Observer<MutableList<WBStatusBean>> {
        var hasNumber = (viewModel.page-2)* PAGESIZE
        var total = it.size
        for (i in hasNumber until total){
            adapter.addItem(adapter.itemCount-1)
        }
    }

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

        //如果不用了就removeObserver
        viewModel.statusList.observe(this.viewLifecycleOwner, observerPageIs2)
        viewModel.currStatus.observe(this.viewLifecycleOwner, observerRefreshError)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("onViewCreated","==================onViewCreated onViewCreated========================")
        initAdapter()
        initRecycler()
        initFloating()
        initRefresh()
    }

    private fun initAdapter() {
        adapter = MyRecyclerAdapter(this,viewModel)
        adapter.onItemClickListener = object : MyRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(view: View,pos:Int) {
                //"你点击了第${pos}Item".toastInfo()
//                var intent = Intent(MyApplication.context, MainActivity2().javaClass)
//                startActivity(intent)
            }
        }
        adapter.onLoadMoreListener = object : MyRecyclerAdapter.OnLoadMoreListener {
            override fun onLoadMore(view: MyFooterViewHolder) {
                //"你调用了onLoadMore".toastInfo()
                viewModel.getStatusesList(TOKEN, viewModel.page++)
                viewModel.statusList.observe(this@NewFragment.viewLifecycleOwner, observerLoadMore)

            }
        }
        adapter.onDeleteImageListener = object : MyRecyclerAdapter.OnDeleteImageListener {
            override fun onDeleteImageListener(position: Int, imageUrl: String?) {
                //"你调用了DeleteImage".toastInfo()
                viewModel.statusList.value?.let {
                    //Log.i("infoRemove", index.toString())
                    Log.i("infoRemoveBefore", it[position].picUrls.size.toString())
                    imageUrl?.let { it1 ->
                        var originUrl = getLarge2MiddleUrl(it1)
                        //删除缩略图的缓存
                        FrescoUtil.removeImageCache(originUrl)
                         (it[position].picUrls as MutableList<String>).remove(originUrl)
                    }
                    adapter.updateItem(position)
                    Log.i("infoRemoveAfter", it[position].picUrls.size.toString())
                }

            }

        }
    }

    private fun initData() {
        viewModel.getStatusesList(TOKEN,viewModel.page++)
    }

    private fun initRecycler() {
        new_rv.layoutManager = LinearLayoutManager(this.context)
        new_rv.adapter = adapter
        Log.i("进initRecycler","initRecycler initRecycler initRecycler")
    }

    private fun initFloating(){
        fab.setOnClickListener {
            refresh_layout.isRefreshing = true
            refresh()
            //设置动画
            var objectAnim : ObjectAnimator = ObjectAnimator.ofFloat(it,"rotation", 0f, 360f)
            //持续1.5秒
            objectAnim.duration = 1500
            //开始
            objectAnim.start()
        }
    }

    private fun initRefresh(){
        //设置可用
        refresh_layout.isEnabled = true
        //加载动画三种颜色轮训
        refresh_layout.setColorSchemeColors(0xff0000,0x00ff00,0x0000ff)
        //refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        //refresh_layout.setProgressBackgroundColorSchemeColor(0x03DAC5);
        refresh_layout.setOnRefreshListener {
            //"触发下啦监听".toastInfo()
            refresh()
            //设置动画
            var  objectAnim : ObjectAnimator = ObjectAnimator.ofFloat(fab,"rotation", 0f, 360f)
            //持续1.5秒
            objectAnim.duration = 1500
            //开始
            objectAnim.start()
        }
    }

    private fun refresh(){
        viewModel.currStatus.value = LoadStatus.LoadMoreIn
        isRefresh = true
        viewModel.page = 1
        viewModel.getStatusesList(TOKEN, viewModel.page++)
    }

    override fun onDetach() {
        Log.i("FragmentLife","=====onDetach==========")
        super.onDetach()
    }
    override fun onPause() {
        Log.i("FragmentLife","=====onPause==========")
        super.onPause()
    }
    override fun onDestroy() {
        Log.i("FragmentLife","=====onPause==========")
        super.onDestroy()
    }

    companion object {
        var isRefresh = false
    }


}