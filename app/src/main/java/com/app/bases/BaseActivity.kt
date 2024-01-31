package com.app.bases


import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.app.R
import com.app.extention.setTransparentStatusBar
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
        setStatusBarGradiant(this)
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

    private fun setStatusBarGradiant(activity: Activity) {
        val window: Window = activity.window
        val background = ContextCompat.getDrawable(activity, R.drawable.gradient_background)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(activity, android.R.color.white)
        window.setBackgroundDrawable(background)
    }

}