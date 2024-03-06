package com.app.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.R
import com.app.databinding.FragmentHomeBinding
import com.app.bases.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun getToolbarBinding() = viewBinding.toolbarHome

    override fun getToolbarTitle() = R.string.empty

    override fun isMenuButton() = true

    override fun onViewCreated(savedInstanceState: Bundle?) {
    }

    override fun addViewsListener() {
    }

    override fun addViewModelObservers() {
    }


}