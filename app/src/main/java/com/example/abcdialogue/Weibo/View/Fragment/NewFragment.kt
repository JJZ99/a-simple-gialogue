package com.example.abcdialogue.Weibo.View.Fragment

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abcdialogue.Module.MainActivity2
import com.example.abcdialogue.MyApplication
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder
import kotlinx.android.synthetic.main.fragment_liner_recycler.new_rv
import kotlinx.android.synthetic.main.fragment_liner_recycler.refresh_layout
import androidx.lifecycle.ViewModelProvider
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastInfo
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastSuccess
import com.example.abcdialogue.Weibo.Adapter.LoadStatus
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter.Companion.PAGESIZE
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter.Companion.currStatus
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter.Companion.page
import com.example.abcdialogue.Weibo.InitSDK.Companion.TOKEN
import com.example.abcdialogue.Weibo.Model.WBViewModelFactory
import com.example.abcdialogue.Weibo.Model.WBViewModel
import com.example.abcdialogue.Weibo.Util.FrescoUtil
import com.example.abcdialogue.Weibo.Util.ParseUtil.getLarge2MiddleUrl
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastError
import kotlinx.android.synthetic.main.fragment_liner_recycler.fab


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
                if (isRefresh){
                    "刷新成功".toastSuccess()
                }
                isRefresh=false
            }
        })
        currStatus.observe(this.viewLifecycleOwner,{
            if (isRefresh && it == LoadStatus.LoadMoreError) {
                refresh_layout.isRefreshing = false
                "下拉刷新失败，请检查网络".toastError()
            }
        })
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
                viewModel.getStatusesList(TOKEN, page++)
                viewModel.statusList.observe(this@NewFragment.viewLifecycleOwner,{
                    var hasNumber = (page-2)* PAGESIZE
                    var total = it.size
                    for (i in hasNumber until total){
                        adapter.addItem(adapter.itemCount-1)
                    }
                })
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
        viewModel.getStatusesList(TOKEN,page++)
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
        currStatus.value = LoadStatus.LoadMoreIn
        viewModel.statusList.value?.clear()
        isRefresh = true
        page = 1
        viewModel.getStatusesList(TOKEN, page++)

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


}