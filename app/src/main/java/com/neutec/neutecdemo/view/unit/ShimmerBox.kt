package com.neutec.neutecdemo.view.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
inline fun <reified T> ShimmerBox(
    boxModifier: Modifier,
    shimmerModifier: Modifier? = null,
    data: String? = null,
    listData: MutableList<T>? = null,
    tag: String? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = boxModifier
    ) {
        if (shimmerModifier != null && (data == null && listData == null)) {
            Box(
                modifier = shimmerModifier
                    .background(color = Color.LightGray, shape = RoundedCornerShape(20.dp))
            )
        } else {
            content()
        }
    }
}