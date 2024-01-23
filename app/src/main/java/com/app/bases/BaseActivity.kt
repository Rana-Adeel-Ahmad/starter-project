package com.app.bases


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.app.utils.TransparentProgressDialog

/**
 * @author Muzzamil Saleem
 */
abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    protected lateinit var viewBinding: VB

    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        viewBinding = initBinding()
        setContentView(viewBinding.root)
        initViews(savedInstanceState)
        addViewModelObservers()
        attachListens()
    }


    abstract fun initBinding(): VB

    abstract fun initViews(savedInstanceState: Bundle?)

    abstract fun addViewModelObservers()

    abstract fun attachListens()


    fun showProgressDialog() {
        TransparentProgressDialog.showProgressDialog(this)
    }

    fun hideProgressDialog() {
        TransparentProgressDialog.hideProgress()
    }

}