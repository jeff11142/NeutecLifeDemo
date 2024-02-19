package com.neutec.neutecdemo.view.unit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetLayout
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetValue
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.neutec.neutecdemo.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomBottomSheetView(
    title: String = "",
    isFullScreen: Boolean = true,
    showConfirmButton: Boolean = true,
    showBottomSheet: MutableState<Boolean>,
    confirmButtonEnable: MutableState<Boolean> = remember {
        mutableStateOf(true)
    },
    confirmButtonClick: () -> Unit = {},
    sheetContent: @Composable () -> Unit
) {
    val bottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    LaunchedEffect(showBottomSheet.value) {
        if (showBottomSheet.value) {
            bottomSheetState.show()
        } else {
            bottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Column(
                modifier =
                if (isFullScreen)
                    Modifier
                        .fillMaxWidth()
                        .height(screenHeight)
                else Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    Image(
                        painter = painterResource(R.mipmap.down_arrow_icon),
                        contentDescription = "close",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.TopStart)
                            .clickable {
                                confirmButtonClick()
                                showBottomSheet.value = false
                            }
                    )

                    Text(text = title, modifier = Modifier.align(Alignment.Center))
                }
                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    sheetContent()
                }

                if (showConfirmButton) {
                    val color = if (confirmButtonEnable.value) Color.Black else Color.LightGray
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(10.dp)
                            .background(color, shape = RoundedCornerShape(50.dp))
                            .clickable(
                                enabled = confirmButtonEnable.value
                            ) {
                                confirmButtonClick()
                                showBottomSheet.value = false
                            }
                    ) {
                        Text(
                            text = "確認",
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

            }

        },
        content = {
        },
        scrimColor = Color.Black.copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        sheetGesturesEnabled = false
    )
}