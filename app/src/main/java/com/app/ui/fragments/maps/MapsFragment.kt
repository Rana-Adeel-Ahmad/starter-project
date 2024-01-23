package com.app.ui.fragments.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.R
import com.app.databinding.FragmentHomeBinding
import com.app.bases.BaseFragment
import com.app.databinding.FragmentMapsBoxBinding
import com.ncms.module.NcmAppCommons

class MapsFragment : BaseFragment<FragmentMapsBoxBinding, MapsViewModel>() {

    override val viewModel: MapsViewModel by viewModels()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMapsBoxBinding {
        return FragmentMapsBoxBinding.inflate(inflater, container, false)
    }

    override fun getToolbarBinding() = viewBinding.toolbar

    override fun getToolbarTitle() = R.string.str_maps

    override fun isMenuButton() = true

    override fun setupUI(savedInstanceState: Bundle?) {
       with(viewBinding.mapView){
           loadStyle()
       }
    }



    override fun attachListener() {
    }

    override fun observeViewModel() {
    }


}