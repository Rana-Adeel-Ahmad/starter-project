package com.app.ui.activities.sidenavigation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.models.MenuItemModel
import com.app.listeners.MenuListener
import com.app.ui.adapters.MenuItemAdapter
import com.app.ui.activities.main.MainViewModel
import com.app.databinding.ActivitySideNavigationBinding
import com.app.bases.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SideNavigationActivity : BaseActivity<ActivitySideNavigationBinding, MainViewModel>(),
    MenuListener,
    NavigationBarView.OnItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: MenuItemAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override val viewModel: MainViewModel by viewModels()

    override fun addViewBinding() = ActivitySideNavigationBinding.inflate(layoutInflater)

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        navController = findNavController(R.id.nav_host_fragment_content_side_navigation)
        bottomNavigationView = viewBinding.appBarSideNavigation.bottomNav
        drawerLayout = viewBinding.drawerLayout
        adapter = MenuItemAdapter(this, this)
        recycler = viewBinding.drawerLayout.findViewById(R.id.recyclerView)
        recycler.adapter = adapter
    }

    override fun addViewModelObservers() {

    }

    override fun isTransparentStatusBar() = true

    override fun addViewsListener() {
        bottomNavigationView.setOnItemSelectedListener(this)
    }


    override fun onMenuItemClicked(menuItem: MenuItemModel) {
        openCloseDrawer()
        Handler(Looper.getMainLooper()).postDelayed({
            when (menuItem.menuID) {
                MenuItemModel.MenuID.HOME -> {
                    bottomNavigationView.selectedItemId = R.id.navigation_home
                }

                MenuItemModel.MenuID.LIVE -> {
                    bottomNavigationView.selectedItemId = R.id.navigation_live
                }

                MenuItemModel.MenuID.OWN_VIDEOS -> {
                    bottomNavigationView.selectedItemId = R.id.navigation_video
                }

                MenuItemModel.MenuID.MESSAGES -> {
                    bottomNavigationView.selectedItemId = R.id.navigation_messages
                }

                MenuItemModel.MenuID.PROFILE -> {
                    bottomNavigationView.selectedItemId = R.id.navigation_profile
                }

                else -> {
                    updateNavigation(menuItem.menuID)
                }

            }
        }, 100)

    }


    override fun getMenuItems(): ArrayList<MenuItemModel> {
        val list = ArrayList<MenuItemModel>()
        list.add(
            MenuItemModel(
                MenuItemModel.MenuID.HOME,
                R.drawable.ic_bottom_home,
                getString(R.string.empty)
            )
        )





        return list
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                updateNavigation(MenuItemModel.MenuID.HOME, true)
            }

            R.id.navigation_live -> {
                updateNavigation(MenuItemModel.MenuID.LIVE, true)
            }

            R.id.navigation_video -> {
                updateNavigation(MenuItemModel.MenuID.OWN_VIDEOS, true)
            }

            R.id.navigation_messages -> {
                updateNavigation(MenuItemModel.MenuID.MESSAGES, true)
            }

            R.id.navigation_profile -> {
                updateNavigation(MenuItemModel.MenuID.PROFILE, true)
            }

            else -> {
                openCloseDrawer()
                return false
            }
        }
        return true
    }


    private fun updateNavigation(menuID: MenuItemModel.MenuID, stopBackStack: Boolean = false) {
        if (stopBackStack) navController.popBackStack()
        when (menuID) {
            MenuItemModel.MenuID.HOME -> {
                navController.navigate(R.id.navigation_home)
            }

            MenuItemModel.MenuID.LIVE -> {
                navController.navigate(R.id.navigation_live)
            }

            MenuItemModel.MenuID.OWN_VIDEOS -> {
                navController.navigate(R.id.navigation_video)
            }

            MenuItemModel.MenuID.MESSAGES -> {
                navController.navigate(R.id.navigation_messages)
            }

            MenuItemModel.MenuID.PROFILE -> {
                navController.navigate(R.id.navigation_profile)
            }

            else -> {
                navController.navigate(R.id.navigation_home)
            }
        }
    }

    open fun openCloseDrawer() {
        try {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}