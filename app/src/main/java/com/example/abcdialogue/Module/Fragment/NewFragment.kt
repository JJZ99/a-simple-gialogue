package com.example.abcdialogue.Module.Fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.Adapter.MyRecyclerAdapter
import com.example.abcdialogue.R
import kotlinx.android.synthetic.main.fragment_liner_recycler.new_rv

class NewFragment: Fragment(R.layout.fragment_liner_recycler) {

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


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        new_rv.layoutManager = LinearLayoutManager(this.context)
        new_rv.addItemDecoration(MyDecoration())
        new_rv.adapter = MyRecyclerAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }


    class MyDecoration() : RecyclerView.ItemDecoration(){
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            //画了一个矩形，就是有点扁
            outRect.set(0,0,0,view.resources.getDimensionPixelOffset(R.dimen.dividerHeight))
        }


    }

}