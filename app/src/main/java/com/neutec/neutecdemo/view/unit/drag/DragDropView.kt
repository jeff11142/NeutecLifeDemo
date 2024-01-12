package com.neutec.neutecdemo.view.unit.drag


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.neutec.neutecdemo.utility.dragAndDrop
import com.neutec.neutecdemo.websocket.ShortCutData
import kotlinx.coroutines.launch

private var itemHeight = 0

enum class SlideState {
    NONE,
    UP,
    DOWN
}

@ExperimentalAnimationApi
@Composable
fun DragDropList(
    modifier: Modifier,
    dragDropList: MutableList<ShortCutData>,
    slideStates: Map<ShortCutData, SlideState>,
    updateSlideState: (shortCutData: ShortCutData, slideState: SlideState) -> Unit,
    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        items(dragDropList.size) { index ->
            val dragDropData = dragDropList.getOrNull(index)
            if (dragDropData != null) {
                key(dragDropData) {
                    val slideState = slideStates[dragDropData] ?: SlideState.NONE
                    DragDropItem(
                        shortCutData = dragDropData,
                        slideState = slideState,
                        dragDropList = dragDropList,
                        updateSlideState = updateSlideState,
                        updateItemPosition = updateItemPosition
                    )
                }

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray))
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun DragDropItem(
    shortCutData: ShortCutData,
    slideState: SlideState,
    dragDropList: MutableList<ShortCutData>,
    updateSlideState: (shortCutData: ShortCutData, slideState: SlideState) -> Unit,
    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
) {
    val itemHeightDp = 50.dp
    with(LocalDensity.current) {
        itemHeight = itemHeightDp.toPx().toInt()
    }
    val verticalTranslation by animateIntAsState(
        targetValue = when (slideState) {
            SlideState.UP -> -itemHeight
            SlideState.DOWN -> itemHeight
            else -> 0
        },
        label = "",
    )
    val isDragged = remember { mutableStateOf(false) }
    val zIndex = if (isDragged.value) 1.0f else 0.0f
    val elevation = if (isDragged.value) 8.dp else 0.dp

    val currentIndex = remember { mutableStateOf(0) }
    val destinationIndex = remember { mutableStateOf(0) }

    val isPlaced = remember { mutableStateOf(false) }
    LaunchedEffect(isPlaced.value) {
        if (isPlaced.value) {
            launch {
                if (currentIndex.value != destinationIndex.value) {
                    updateItemPosition(currentIndex.value, destinationIndex.value)
                }
                isPlaced.value = false
            }
        }
    }

    Box(
        Modifier
            .dragAndDrop(
                shortCutData,
                dragDropList,
                itemHeight,
                updateSlideState,
                isDraggedAfterLongPress = true,
                { isDragged.value = true },
                { cIndex, dIndex ->
                    isDragged.value = false
                    isPlaced.value = true
                    currentIndex.value = cIndex
                    destinationIndex.value = dIndex
                }
            )
            .offset { IntOffset(0, verticalTranslation) }
            .zIndex(zIndex)
            .height(itemHeightDp)
    ) {
        Column(
            modifier = Modifier
                .height(40.dp)
                .shadow(elevation, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(
                    color = Color.White
                )
                .align(Alignment.CenterStart)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                shortCutData.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}