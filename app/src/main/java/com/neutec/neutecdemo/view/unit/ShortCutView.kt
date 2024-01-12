package com.neutec.neutecdemo.view.unit

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.asLiveData
import com.neutec.neutecdemo.repository.AccountManager
import com.neutec.neutecdemo.view.unit.drag.DragDropList
import com.neutec.neutecdemo.view.unit.drag.SlideState
import com.neutec.neutecdemo.websocket.ShortCutData

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShortCutView() {
    val quickCategoryList = AccountManager.quickCategoryList.asLiveData().observeAsState()
    val dragDropList = remember { mutableStateListOf(*(arrayOf<ShortCutData>())) }
    LaunchedEffect(quickCategoryList.value) {
        quickCategoryList.value?.let { list ->
            dragDropList.clear()
            dragDropList.addAll(list)
        }
    }

    val slideStates = remember {
        mutableStateMapOf<ShortCutData, SlideState>()
            .apply {
                dragDropList.associateWith { _ ->
                    SlideState.NONE
                }.also {
                    putAll(it)
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (dragDropList.isNotEmpty()) {
            DragDropList(
                modifier = Modifier.fillMaxWidth(),
                dragDropList = dragDropList,
                slideStates = slideStates,
                updateSlideState = { shortCutData, slideState ->
                    slideStates[shortCutData] = slideState
                },
                updateItemPosition = { currentIndex, destinationIndex ->
                    val dragDropData = dragDropList[currentIndex]
                    dragDropList.removeAt(currentIndex)
                    dragDropList.add(destinationIndex, dragDropData)
                    slideStates.apply {
                        dragDropList.associateWith {
                            SlideState.NONE
                        }.also {
                            putAll(it)
                        }
                    }
                }
            )
        }
    }
}