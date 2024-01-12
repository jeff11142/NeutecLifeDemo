package com.neutec.neutecdemo.view.screen.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.neutec.neutecdemo.R
import com.neutec.neutecdemo.view.unit.PageTopView
import com.neutec.neutecdemo.viewmodel.NotificationPageViewModel


@Composable
fun NotificationDetailPage(fullScreenNavController: NavHostController) {
    val viewModel: NotificationPageViewModel = viewModel()
    val selectedNotificationData = viewModel.selectedNotificationData.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PageTopView(
            showBackIcon = true,
            title = selectedNotificationData.value?.category ?: "",
            backIconClickEvent = {
                fullScreenNavController.popBackStack()
            })
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = selectedNotificationData.value?.title ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                if (selectedNotificationData.value?.contentImageUrl?.isNotEmpty() == true) {
                    Spacer(modifier = Modifier.height(20.dp))

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(selectedNotificationData.value?.contentImageUrl ?: "")
                            .decoderFactory(ImageDecoderDecoder.Factory())
                            .build(),
                        contentDescription = "Notification Detail Image",
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 1.dp, bottom = 20.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.notification_detail_content),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        Text(
                            text = selectedNotificationData.value?.datetime ?: "",
                            fontSize = 13.sp,
                            color = Color.LightGray,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        Text(
                            text = selectedNotificationData.value?.content ?: "",
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
