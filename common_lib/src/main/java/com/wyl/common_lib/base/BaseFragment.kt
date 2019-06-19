package com.wyl.common_lib.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment(), Presenter {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun loadData()

    override fun onClick(v: View) {}

    override fun getItemDecoration(): RecyclerView.ItemDecoration? = null

    override fun getAdapter(): RecyclerView.Adapter<*>? = null
}