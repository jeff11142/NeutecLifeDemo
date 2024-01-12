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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.neutec.neutecdemo.R

@Composable
fun DraggableBottomView(modifier: Modifier, bottomHeight: MutableState<Dp>) {
    var viewHeight by rememberSaveable(saver = MutableStateDpSaver) { mutableStateOf(0.dp) }
    var needAnimateScroll by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val thresholdHeight = screenHeight * 4 / 5
    val maxCornerRadius = 60.dp
    val cornerRadius =
        (maxCornerRadius * (1 - (viewHeight) / (screenHeight))).coerceIn(0.dp, maxCornerRadius)
    var viewOnTop by remember { mutableStateOf(false) }
    val animatedHeight by animateDpAsState(
        targetValue = viewHeight,
        label = "viewHeight",
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    ) {
        viewOnTop = viewHeight == screenHeight
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(if (needAnimateScroll) animatedHeight else viewHeight)
            .background(Color.Transparent)
    ) {
        LaunchedEffect(bottomHeight.value) {
            if (viewHeight.value.dp == 0.dp) {
                viewHeight = bottomHeight.value
            }
        }

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
                                            val newHeight = viewHeight + dragAmountDp
                                            viewHeight =
                                                newHeight.coerceIn(bottomHeight.value, screenHeight)
                                            change.consume()
                                        },
                                        onDragEnd = {
                                            // 手勢結束時的處理
                                            if (viewHeight > thresholdHeight) {
                                                needAnimateScroll = true
                                                viewHeight = screenHeight
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
                                .clickable {
                                    viewHeight = bottomHeight.value
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

val MutableStateDpSaver: Saver<MutableState<Dp>, Float> = Saver(
    save = { it.value.value },
    restore = { mutableStateOf(Dp(it)) }
)