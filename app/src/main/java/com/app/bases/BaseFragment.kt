package com.app.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.app.R
import com.app.ui.activities.sidenavigation.SideNavigationActivity
import com.app.databinding.ToolBarLayoutBinding
import com.app.extention.addStatusBarMargin
import com.app.utils.TransparentProgressDialog

/**
 * @author Muzzamil Saleem
 */
abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    protected abstract val viewModel: VM

    protected lateinit var viewBinding: VB

    protected val mainActivity by lazy {
        requireActivity() as SideNavigationActivity
    }

    private var toolbar: ToolBarLayoutBinding? = null


    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun getToolbarBinding(): ToolBarLayoutBinding?

    abstract fun getToolbarTitle(): Int

    abstract fun onViewCreated(savedInstanceState: Bundle?)

    abstract fun addViewsListener()

    abstract fun addViewModelObservers()

    abstract fun isMenuButton(): Boolean


    var sav: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sav = savedInstanceState
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = getToolbarBinding()
        initToolbar()
        onViewCreated(savedInstanceState)
        addViewsListener()
        addViewModelObservers()
    }


    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = getBinding(inflater, container)
        return viewBinding.root
    }


    private fun initToolbar() {
        toolbar?.let {
            it.root.addStatusBarMargin()
            with(it) {
                tvToolbarTitle.text = getString(getToolbarTitle())
                btnToolbarStart.setImageResource(if (isMenuButton()) R.drawable.ic_menu else R.drawable.ic_back)
                btnToolbarStart.setOnClickListener {
                    if (isMenuButton()) {
                        mainActivity.openCloseDrawer()
                    } else {
                        mainActivity.onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        }
    }

    fun showProgressDialog() {
        TransparentProgressDialog.showProgressDialog(mainActivity)
    }

    fun hideProgressDialog() {
        TransparentProgressDialog.hideProgress()
    }


}