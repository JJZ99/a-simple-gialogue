package com.example.abcdialogue.Weibo.View.Fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter
import com.example.abcdialogue.R
import com.example.abcdialogue.Util.Util.toastShort
import kotlinx.android.synthetic.main.fragment_liner_recycler.add_btn
import kotlinx.android.synthetic.main.fragment_liner_recycler.new_rv
import kotlinx.android.synthetic.main.fragment_liner_recycler.remove_btn

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
        var adapter = MyRecyclerAdapter()
        new_rv.layoutManager = LinearLayoutManager(this.context)
        //new_rv.addItemDecoration(MyDecoration())
        new_rv.adapter = adapter
        adapter.onItemClickListener = object : MyRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(view: View,pos:Int) {
                "你点击了第${pos}Item".toastShort(view.context)
            }
        }
        add_btn.setOnClickListener{
            adapter.addItem(0)
        }
        remove_btn.setOnClickListener{
            adapter.removeItem(adapter.itemCount-1)
        }
        //设置每一项的动画
        //new_rv.itemAnimator
    //new_rv.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->  }
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