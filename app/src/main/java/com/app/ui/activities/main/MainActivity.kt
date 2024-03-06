package com.app.ui.activities.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.app.ui.activities.sidenavigation.SideNavigationActivity
import com.app.databinding.ActivityMainBinding
import com.app.bases.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun addViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        startActivity(Intent(this, SideNavigationActivity::class.java))
    }

    override fun addViewModelObservers() {
    }

    override fun addViewsListener() {
    }


    override fun onStart() {
        super.onStart()
        Log.d("MAINA" , "onStart")
    }

    override fun isTransparentStatusBar() = true

}