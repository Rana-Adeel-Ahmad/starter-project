package com.app.bases

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.app.constants.AppConstants.ARABIC
import com.app.constants.AppConstants.ENGLISH
import com.app.extention.isArabic
import com.app.extention.setTransparentStatusBar
import com.app.utils.TransparentProgressDialog

/**
 * @author Muzzamil Saleem
 */
abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    protected lateinit var viewBinding: VB

    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        setAppLocale()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (isTransparentStatusBar()) setTransparentStatusBar()
        super.onCreate(savedInstanceState)
        viewBinding = addViewBinding()
        setContentView(viewBinding.root)
        onActivityCreate(savedInstanceState)
        addViewModelObservers()
        addViewsListener()
    }

    abstract fun addViewBinding(): VB
    abstract fun onActivityCreate(savedInstanceState: Bundle?)
    abstract fun addViewsListener()
    abstract fun addViewModelObservers()
    abstract fun isTransparentStatusBar(): Boolean

    fun showProgressDialog() {
        TransparentProgressDialog.showProgressDialog(this)
    }

    fun hideProgressDialog() {
        TransparentProgressDialog.hideProgress()
    }

    private fun setAppLocale() {
        val languageString = if (isArabic()) ARABIC else ENGLISH
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageString)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

}