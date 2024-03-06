package com.app.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.app.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<VB : ViewBinding>() : BottomSheetDialogFragment() {

    protected lateinit var viewBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = getBinding(inflater, container)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(savedInstanceState)
        addViewsListener()
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun onViewCreated(savedInstanceState: Bundle?)

    abstract fun addViewsListener()


}