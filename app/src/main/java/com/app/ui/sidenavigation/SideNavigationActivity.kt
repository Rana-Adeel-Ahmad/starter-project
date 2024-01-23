package com.app.ui.sidenavigation

import android.os.Bundle
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
import com.app.ui.main.MainViewModel
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

    override fun initBinding() = ActivitySideNavigationBinding.inflate(layoutInflater)

    override fun initViews(savedInstanceState: Bundle?) {
        navController = findNavController(R.id.nav_host_fragment_content_side_navigation)
        bottomNavigationView = viewBinding.appBarSideNavigation.bottomNav
        drawerLayout = viewBinding.drawerLayout
        adapter = MenuItemAdapter(this, this)
        recycler = viewBinding.drawerLayout.findViewById(R.id.recyclerView)
        recycler.adapter = adapter

    }

    override fun addViewModelObservers() {

    }

    override fun attachListens() {

        bottomNavigationView.setOnItemSelectedListener(this)

    }


    override fun onMenuItemClicked(menuItem: MenuItemModel) {
        openCloseDrawer()
        drawerLayout.close()
        updateNavigation(menuItem.menuID)
    }


    override fun getMenuItems(): ArrayList<MenuItemModel> {
        val list = ArrayList<MenuItemModel>()
        list.add(
            MenuItemModel(
                MenuItemModel.MenuID.WEATHER,
                R.drawable.ic_home_black_24dp,
                getString(R.string.menu_home)
            )
        )
        list.add(
            MenuItemModel(
                MenuItemModel.MenuID.SETTINGS,
                R.drawable.ic_home_black_24dp,
                getString(R.string.menu_settings)
            )
        )


        return list
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.navigation_weather -> {
                viewBinding.appBarSideNavigation.indicatorView.updateRectByIndex(0, true)
                updateNavigation(MenuItemModel.MenuID.WEATHER, true)
            }

            R.id.navigation_maps -> {
                viewBinding.appBarSideNavigation.indicatorView.updateRectByIndex(1, true)
                updateNavigation(MenuItemModel.MenuID.MAPS, true)
            }

            R.id.navigation_marine -> {
                viewBinding.appBarSideNavigation.indicatorView.updateRectByIndex(2, true)
                updateNavigation(MenuItemModel.MenuID.MARINE, true)
            }

            R.id.navigation_events -> {
                viewBinding.appBarSideNavigation.indicatorView.updateRectByIndex(3, true)
                updateNavigation(MenuItemModel.MenuID.EARTH_EVENTS, true)
            }

            R.id.navigation_more -> {
                openCloseDrawer()
                return false
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
            MenuItemModel.MenuID.WEATHER -> {
                navController.navigate(R.id.navigation_weather)
            }

            MenuItemModel.MenuID.MAPS -> {
                navController.navigate(R.id.navigation_maps)
            }

            MenuItemModel.MenuID.MARINE -> {
                navController.navigate(R.id.navigation_marine)
            }

            MenuItemModel.MenuID.EARTH_EVENTS -> {
                navController.navigate(R.id.navigation_events)
            }

            MenuItemModel.MenuID.SETTINGS -> {
                navController.navigate(R.id.navigation_settings)
            }

            else -> {
                navController.navigate(R.id.navigation_weather)
            }
        }
    }

    open fun openCloseDrawer() {
        if (drawerLayout.isOpen) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            drawerLayout.openDrawer(GravityCompat.END)
        }
    }

}