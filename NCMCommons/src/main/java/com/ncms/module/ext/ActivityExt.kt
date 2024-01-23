package com.ncms.module.ext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


fun <T> AppCompatActivity.findFragmentById(@IdRes id: Int): T =
    supportFragmentManager.findFragmentById(id) as T

inline fun AppCompatActivity.setFragment(containerViewId: Int, f: () -> Fragment): Fragment {
    val manager = supportFragmentManager
    val fragment = manager.findFragmentById(containerViewId)
    fragment?.let { return it }
    return f().apply { manager.beginTransaction().add(containerViewId, this).commit() }
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)