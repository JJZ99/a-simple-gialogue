package com.example.abcdialogue.Weibo.Model


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastError
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastInfo
import com.example.abcdialogue.Weibo.Adapter.LoadStatus
import com.example.abcdialogue.Weibo.Adapter.LoadStatus.*
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter.Companion.currStatus
import com.example.abcdialogue.Weibo.Bean.CountryBean
import com.example.abcdialogue.Weibo.Bean.WBAllDTO
import com.example.abcdialogue.Weibo.Bean.WBStatusBean
import com.example.abcdialogue.Weibo.Bean.transformToBean
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastSuccess
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class WBViewModel : ViewModel(){


    val countryList = MutableLiveData<List<CountryBean>>()
    val statusList = MutableLiveData<MutableList<WBStatusBean>>()



    //也可以参考CommentViewModel。kt 140的写法
    fun getProvinceList(token: String) {
        DataFetchModel.getProvinceList(token)
            .subscribe(object : Observer<List<Map<String, String>>> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(t: List<Map<String, String>>) {
                    Log.i("https:", t.toString())
                    val list = mutableListOf<CountryBean>()
                    for (i in t.indices) {
                        list.add(CountryBean(t[i].keys.first(), t[i].values.first()))
                    }
                    countryList.value = list
                }
                override fun onError(e: Throwable) {
                    "城市数据请求失败".toastError()
                    e.printStackTrace()
                }
            })
    }

    fun getStatusesList(token:String,page:Int){
        Log.i("页数","===============$page===============")
        //"=======第{$page}页======".toastInfo()
        DataFetchModel.getStatusesList(token,page)
            .subscribe(object : Observer<WBAllDTO> {
                override fun onComplete() {
                    currStatus.value = LoadMoreSuccess
                }
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(resp: WBAllDTO) {
                    if (statusList.value == null){
                        statusList.value = mutableListOf()
                    }
                    //这里是分页每请求一次，就把进请求的数据追加到后面，然后触发观察者
                    statusList.value = statusList.value?.apply {
                        addAll(resp.statuses?.map { dto ->
                            dto.transformToBean()
                        } ?: listOf())
                    }
                    //可以删除下面两行
                    Log.i("get statueslist:", statusList.value.toString())
                  //  string.value = statusList.value.toString()
                }
                override fun onError(e: Throwable) {
                    currStatus.value = LoadMoreError
                    "微博数据请求失败".toastError()
                    e.printStackTrace()
                }
            })

    }


    override fun onCleared() {
        super.onCleared()
    }
}