package com.example.abcdialogue.Weibo.VM

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.abcdialogue.Weibo.Bean.CountryBean
import com.example.abcdialogue.Weibo.Bean.WBAllDTO
import com.example.abcdialogue.Weibo.Bean.WBStatusBean
import com.example.abcdialogue.Weibo.Bean.transformToBean
import com.example.abcdialogue.Weibo.Model.DataFetchModel
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class CountryViewModel : ViewModel(){

    private val disposables: CompositeDisposable by lazy{
        CompositeDisposable()
    }

    fun addDisposable(d: Disposable){
        disposables.add(d)
    }

    val countryList = MutableLiveData<List<CountryBean>>()
    val statusList = MutableLiveData<List<WBStatusBean>>()
    val string = MutableLiveData<String>()



    //也可以参考CommentViewModel。kt 140的写法
    fun getProvinceList(token: String) {
        DataFetchModel.getProvinceList(token)
            .subscribe(object : Observer<List<Map<String, String>>> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
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
                    e.printStackTrace()
                }
            })
    }

    fun getStatusesList(token:String){
        DataFetchModel.getStatusesList(token)
            .subscribe(object : Observer<WBAllDTO> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                }
                override fun onNext(resp: WBAllDTO) {

                    statusList.value = mutableListOf<WBStatusBean>().apply {
                        addAll(resp.statuses?.map { dto ->
                            dto.transformToBean()
                        } ?: listOf())
                    }
                    Log.i("https:", resp.toString())
                    statusList.value
                    string.value = resp.toString()
                }
                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })

    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}