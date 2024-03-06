package com.app.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.app.ui.activities.main.MainViewModel
import com.app.ui.activities.sidenavigation.SideNavigationActivity
import com.app.databinding.ActivitySplashBinding
import com.app.bases.BaseActivity
import com.app.preferences.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, MainViewModel>() {

    private val delay: Long = 3000

    override val viewModel: MainViewModel by viewModels()

    override fun addViewBinding() = ActivitySplashBinding.inflate(layoutInflater)


    override fun onActivityCreate(savedInstanceState: Bundle?) {

    }

    override fun addViewModelObservers() {

    }

    override fun addViewsListener() {
        viewBinding.btnEnglish.setOnClickListener {
            PreferencesHelper.setAppLanguage(false)
            startNextActivity()
        }

        viewBinding.btnArabic.setOnClickListener {
            PreferencesHelper.setAppLanguage(true)
            startNextActivity()
        }
    }

    override fun isTransparentStatusBar() = true

    private fun startNextActivity() {
        startActivity(Intent(this, SideNavigationActivity::class.java))
        this.finish()
    }


}