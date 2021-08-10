package com.example.abcdialogue.Weibo.VM

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.abcdialogue.Weibo.Bean.CountryDTO
import com.example.abcdialogue.Weibo.Model.DataFetchModel
import com.example.abcdialogue.Weibo.Model.WeiBoApi
import com.example.abcdialogue.Weibo.Util.Net.RetrofitHelper
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CountryViewModel : ViewModel(){

    private val disposables: CompositeDisposable by lazy{
        CompositeDisposable()
    }

    fun addDisposable(d: Disposable){
        disposables.add(d)
    }

    val countryList = MutableLiveData<List<CountryDTO>>()


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
                    val list = mutableListOf<CountryDTO>()
                    for (i in t.indices) {
                        list.add(CountryDTO(t[i].keys.first(), t[i].values.first()))
                    }
                    countryList.value = list
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