package com.neutec.neutecdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _nowTabPage = MutableLiveData(MainTabPage.HOME)
    val nowTabPage: LiveData<MainTabPage> get() = _nowTabPage

    fun setNowTabPage(tabPage: MainTabPage) {
        _nowTabPage.value = tabPage
    }
}

enum class MainTabPage {
    HOME,
    CATEGORY,
    NOTIFICATION,
    SETTING
}