package com.example.selectitemrecyclerviewwithcharanphabet

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseCustomView<T: ViewDataBinding> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    protected val binding : T

    @LayoutRes
    protected abstract fun layoutRes() : Int

    protected abstract fun initView()

    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, layoutRes(), this, true)
        initView()
    }

}