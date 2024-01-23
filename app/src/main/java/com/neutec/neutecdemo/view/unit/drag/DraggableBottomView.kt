package com.neutec.neutecdemo.view.unit.drag

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neutec.neutecdemo.R
import com.neutec.neutecdemo.utility.neutecClickable
import com.neutec.neutecdemo.viewmodel.HomeViewModel

@Composable
fun DraggableBottomView(modifier: Modifier, bottomHeight: MutableState<Dp>) {
    val viewModel: HomeViewModel = viewModel()
    val draggableBottomViewHeight =
        viewModel.draggableBottomViewHeight.asLiveData().observeAsState()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    var needAnimateScroll by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val thresholdHeight = screenHeight * 4 / 5
    val maxCornerRadius = 60.dp
    val cornerRadius =
        (maxCornerRadius * (1 - (draggableBottomViewHeight.value
            ?: bottomHeight.value) / (screenHeight))).coerceIn(0.dp, maxCornerRadius)
    var viewOnTop by remember { mutableStateOf(false) }
    val animatedHeight by animateDpAsState(
        targetValue = draggableBottomViewHeight.value ?: 0.dp,
        label = "viewHeight",
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    ) {
        viewOnTop = (draggableBottomViewHeight.value ?: 0.dp) == screenHeight
    }

    LaunchedEffect(bottomHeight.value) {
        if (draggableBottomViewHeight.value != null &&
            draggableBottomViewHeight.value != 0.dp &&
            draggableBottomViewHeight.value!! < bottomHeight.value
        ) {
            viewModel.setDraggableBottomViewHeight(bottomHeight.value)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                when {
                    needAnimateScroll -> animatedHeight
                    (draggableBottomViewHeight.value
                        ?: 0.dp) < bottomHeight.value -> bottomHeight.value

                    else -> draggableBottomViewHeight.value ?: 0.dp
                }
            )
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .padding(horizontal = 10.dp)
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    shadowElevation = cornerRadius.toPx()
                    shape = RectangleShape
                    clip = false
                }
                .background(Color.Transparent)
        ) {}

        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(
                topStart = cornerRadius,
                topEnd = cornerRadius,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                ) {
                    if (!viewOnTop) {
                        Image(
                            painter = painterResource(R.mipmap.drag_icon),
                            contentDescription = "drag",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(30.dp)
                                .pointerInput(Unit) {
                                    detectVerticalDragGestures(
                                        onVerticalDrag = { change, dragAmount ->
                                            val dragAmountDp = with(density) { -dragAmount.toDp() }
                                            val draggableDp =
                                                draggableBottomViewHeight.value ?: 0.dp
                                            viewModel.setDraggableBottomViewHeight(
                                                draggableDp
                                                    .plus(
                                                        dragAmountDp
                                                    )
                                                    .coerceIn(bottomHeight.value, screenHeight)
                                            )
                                            change.consume()
                                        },
                                        onDragEnd = {
                                            val draggableDp =
                                                draggableBottomViewHeight.value
                                                    ?: 0.dp

                                            if (draggableDp > thresholdHeight) {
                                                needAnimateScroll = true
                                                viewModel.setDraggableBottomViewHeight(screenHeight)
                                            } else {
                                                needAnimateScroll = false
                                            }
                                        })
                                }
                                .align(Alignment.TopCenter)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.mipmap.down_arrow_icon),
                            contentDescription = "down",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(start = 20.dp)
                                .neutecClickable {
                                    viewModel.setDraggableBottomViewHeight(bottomHeight.value)
                                    viewOnTop = false
                                }
                                .align(Alignment.TopStart)
                        )
                    }
                }
            }
        }
    }
}