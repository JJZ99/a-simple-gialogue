package com.example.abcdialogue.Weibo.View.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog




class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var mBehavior: View? = null;

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    // create dialog
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        Log.e("xiaxl: ", "onCreateDialog")
//        return super.onCreateDialog(savedInstanceState)
//    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

       // 设置弹窗的高度
        Log.e("xiaxl: ", "onCreateDialog")
        super.onCreate(savedInstanceState)
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, com.example.abcdialogue.R.layout.fragment_liner_recycler3, null)
        // amount = view.findViewById(R.id.amount);
        //   pay_root = view.findViewById(R.id.pay_root);
        dialog.setContentView(view)
        dialog.window!!.findViewById<View>(com.example.abcdialogue.R.id.good_rv)
            .setBackgroundResource(android.R.color.background_dark)
      //  mBehavior = BottomSheetBehavior.from(view.parent as View)
        val layoutParams = view.layoutParams
        val height = (resources.displayMetrics.heightPixels * 0.6).toInt() //屏幕高的60%
        layoutParams.height = height
        view.layoutParams = layoutParams
        return dialog
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        Log.e("xiaxl: ", "onCreateView")
//        val inflate: View = inflater.inflate(com.example.abcdialogue.R.layout.fragment_liner_recycler3, container)
//        //
//        initData()
//        return inflate
//    }

    // 加载数据
    private fun initData() {
        // 数据
        val args = arguments
        val feedId = args!!.getLong("FEED_ID", -1)
    }

    override fun onDestroy() {
        Log.e("xiaxl: ", "onDestroy")

        super.onDestroy()
    }

    override fun onDestroyView() {
        Log.e("xiaxl: ", "onDestroyView")

        super.onDestroyView()
    }

    override fun onPause() {
        Log.e("xiaxl: ", "onPause")

        super.onPause()
    }

    override fun onResume() {
        Log.e("xiaxl: ", "onResume")

        super.onResume()
    }

    override fun onStop() {
        Log.e("xiaxl: ", "onStop")

        super.onStop()
    }

    override fun onCancel(dialog: DialogInterface) {
        Log.e("xiaxl: ", "onCancel")

        super.onCancel(dialog)
    }

    override fun onDetach() {
        Log.e("xiaxl: ", "onDetach")

        super.onDetach()
    }

    override fun onAttach(context: Context) {
        Log.e("xiaxl: ", "onAttach")

        super.onAttach(context)
    }

    companion object {
        // 构造方法
        fun newInstance(feedId: Long?): MyBottomSheetDialogFragment {
            val args = Bundle()
            args.putLong("FEED_ID", feedId!!)
            val fragment = MyBottomSheetDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
