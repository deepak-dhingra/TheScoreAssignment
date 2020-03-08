package com.challenge.theScore.framework.baseclasses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseViewModelFragment<B : ViewDataBinding, V : AndroidViewModel> : BaseFragment<B>() {

    lateinit var viewModel: V

    abstract fun getViewModel(): Class<V>

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
                .create(getViewModel())
    }
}