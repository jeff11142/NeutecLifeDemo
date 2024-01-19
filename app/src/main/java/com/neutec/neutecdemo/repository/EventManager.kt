package com.neutec.neutecdemo.repository

import com.neutec.neutecdemo.view.screen.EventItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object EventManager {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val _newBooksInThisMonthFavoriteList =
        MutableSharedFlow<MutableList<EventItemData>>(replay = 1)
    val newBooksInThisMonthFavoriteList: SharedFlow<MutableList<EventItemData>> =
        _newBooksInThisMonthFavoriteList.asSharedFlow()


    private val _currentCategory =
        MutableSharedFlow<Int>(replay = 1)
    val currentCategory: SharedFlow<Int> =
        _currentCategory.asSharedFlow()

    private val _booksSort =
        MutableSharedFlow<String>(replay = 1)
    val booksSort: SharedFlow<String> =
        _booksSort.asSharedFlow()


    suspend fun addOrDeleteNewBooksInThisMonthFavoriteDataToList(data: EventItemData) {
        scope.launch {
            val copyList =
                _newBooksInThisMonthFavoriteList.replayCache.firstOrNull()?.toMutableList()
                    ?: mutableListOf()
            if (copyList.contains(data)) {
                copyList.remove(data)
            } else {
                copyList.add(data)
            }
            _newBooksInThisMonthFavoriteList.emit(copyList)
        }
    }

    suspend fun setCurrentCategory(category: Int) {
        scope.launch {
            _currentCategory.emit(category)
        }
    }

    suspend fun setBooksSort(sort: String) {
        scope.launch {
            _booksSort.emit(sort)
        }
    }
}