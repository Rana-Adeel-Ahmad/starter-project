package com.app.ui.fragments.setting


import com.app.bases.BaseViewModel
import com.app.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val repository: MainRepository
) : BaseViewModel() {


}
