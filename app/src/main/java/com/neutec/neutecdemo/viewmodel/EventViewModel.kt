package com.neutec.neutecdemo.viewmodel

import androidx.lifecycle.asLiveData
import com.neutec.neutecdemo.repository.EventManager

class EventViewModel : WebsocketViewModel() {
    val newBooksInThisMonthFavoriteList = EventManager.newBooksInThisMonthFavoriteList.asLiveData()
    val currentCategory = EventManager.currentCategory.asLiveData()
    val booksSort = EventManager.booksSort.asLiveData()

    suspend fun setCurrentCategory(category: Int) {
        EventManager.setCurrentCategory(category)
    }

    suspend fun setBooksSort(sort: String) {
        EventManager.setBooksSort(sort)
    }
}