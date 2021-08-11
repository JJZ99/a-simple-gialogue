package com.example.abcdialogue.Weibo.View.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter.Companion.page
import com.example.abcdialogue.Weibo.InitSDK
import com.example.abcdialogue.Weibo.Model.WBViewModel
import com.example.abcdialogue.Weibo.Model.CountryViewModelFactory
import kotlinx.android.synthetic.main.fragment_liner_recycler2.country
import kotlinx.android.synthetic.main.fragment_liner_recycler2.get_country

class VideoFragment(): Fragment(R.layout.fragment_liner_recycler2) {

    private val viewModel by lazy{
        ViewModelProvider(this, CountryViewModelFactory()).get(WBViewModel::class.java)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        get_country.setOnClickListener {
            viewModel.getProvinceList(InitSDK.TOKEN.value.toString())
        }
        this.activity?.let { it ->
            viewModel.countryList.observe(it,{
                country.text = it.toString()
            })
        }

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

}