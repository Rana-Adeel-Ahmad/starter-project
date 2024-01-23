package com.app.ui.fragments.marine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.R
import com.app.databinding.FragmentHomeBinding
import com.app.bases.BaseFragment

class MarineFragment : BaseFragment<FragmentHomeBinding, MarineViewModel>() {

    override val viewModel: MarineViewModel by viewModels()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun getToolbarBinding() = viewBinding.toolbar

    override fun getToolbarTitle() = R.string.str_marine

    override fun isMenuButton() = true

    override fun setupUI(savedInstanceState: Bundle?) {
    }

    override fun attachListener() {
    }

    override fun observeViewModel() {
    }


}