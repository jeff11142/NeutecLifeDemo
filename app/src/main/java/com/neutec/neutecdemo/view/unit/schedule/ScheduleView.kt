package com.neutec.neutecdemo.view.unit.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val cardHeight = 60.dp
val textWidth = 45.dp
val textHeight = 40.dp
val circleSize = 25.dp

data class ScheduleEventData(
    val name: String,
    val desc: String
)

data class SelectedTimeData(
    var startTime: String,
    var endTime: String,
    val event: ScheduleEventData?,
    val enableCancel: Boolean = false
)

@Composable
fun ScheduleNoticeView(modifier: Modifier){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Blue)
    ){
        Text(text = "提醒：需選擇連續時段", modifier = Modifier.align(Alignment.Center))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleSelectView(selectedTimeList: SnapshotStateList<SelectedTimeData>) {
    val timeList = generateTimeList("07:00", "20:00", 30)

    val timeTextHeight = remember { mutableStateOf(0) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth - textWidth

    Layout(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        content = {
            TimeLineLayer(timeList, timeTextHeight)
            TimeCardLayout(timeList, selectedTimeList)
        }) { measurable, constraints ->
        val placeable = measurable.mapIndexed { index, measurable ->
            if (index == 1) {
                val cardConstraints =
                    Constraints.fixed(
                        width = cardWidth.roundToPx(),
                        height = cardHeight.roundToPx()
                    )
                measurable.measure(cardConstraints)
            } else {
                val textConstraints =
                    Constraints(maxWidth = textWidth.roundToPx(), maxHeight = Constraints.Infinity)
                measurable.measure(textConstraints)
            }
        }

        val totalHeight =
            timeTextHeight.value * timeList.size + (cardHeight.roundToPx() - timeTextHeight.value) * (timeList.size - 1)

        layout(constraints.maxWidth, totalHeight) {
            placeable.forEachIndexed { index, placeable ->
                val padding = 20.dp.roundToPx()
                if (index == 1) {
                    placeable.placeRelative(
                        x = textWidth.roundToPx() + padding,
                        y = timeTextHeight.value / 2
                    )
                } else {
                    placeable.placeRelative(x = padding, y = 0)
                }
            }
        }
    }
}

@Composable
fun TimeLineLayer(timeList: List<String>, timeTextHeight: MutableState<Int>) {
    Layout(
        content = {
            timeList.forEach {
                Text(text = it)
            }
        }
    ) { measurable, constraints ->
        val placeable = measurable.map {
            val textConstraints =
                Constraints(
                    maxWidth = textWidth.roundToPx(), maxHeight = textHeight.roundToPx()
                )
            it.measure(textConstraints)
        }

        var position = 0
        timeTextHeight.value = placeable[0].height
        val totalHeight =
            timeTextHeight.value * timeList.size + (cardHeight.roundToPx() - timeTextHeight.value) * (timeList.size - 1)

        layout(textWidth.roundToPx(), totalHeight) {
            placeable.forEachIndexed { _, placeable ->
                placeable.placeRelative(x = 0, y = position)
                position += textHeight.roundToPx() + (cardHeight - textHeight).roundToPx()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeCardLayout(timeList: List<String>, selectedTimeList: MutableList<SelectedTimeData>) {
    val cardDataList = List(timeList.size - 1) {}
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth - textWidth - 40.dp
    Layout(
        content = {
            cardDataList.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                addOrMergeTimeSlot(timeList[index], selectedTimeList, 30)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.LightGray,
                            contentColor = Color.Black
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(circleSize)
                                    .background(Color.White, shape = CircleShape)
                                    .border(1.dp, Color.Gray, shape = CircleShape)
                            )
                        }
                    }
                }
            }
            CardSelectView(timeList, selectedTimeList)
        }
    ) { measurable, constraints ->
        val placeable = measurable.mapIndexed { index, measurable ->
//            if (index < cardDataList.size - 1) {
//                val cardHeight = cardHeight.roundToPx()
//                val cardConstraints =
//                    Constraints.fixed(width = cardWidth.roundToPx(), height = cardHeight)
//                measurable.measure(cardConstraints)
//            } else {
//                val selectedConstraints =
//                    Constraints.fixed(width = constraints.maxWidth, height = constraints.maxHeight)
//                measurable.measure(selectedConstraints)
//            }
            val cardHeight = cardHeight.roundToPx()
            val cardConstraints =
                Constraints.fixed(width = cardWidth.roundToPx(), height = cardHeight)
            measurable.measure(cardConstraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var position = 0
            placeable.forEachIndexed { index, eachPlaceable ->
                if (index != placeable.lastIndex) {
                    eachPlaceable.placeRelative(x = 0, y = position)
                    position += eachPlaceable.height
                } else {
                    eachPlaceable.placeRelative(x = 0, y = 0)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardSelectView(
    timeList: List<String>,
    selectedTime: MutableList<SelectedTimeData>
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth - textWidth - 40.dp
    Layout(
        content = {
            selectedTime.forEach {
                val containerColor = if (it.enableCancel) {
                    Color.Black
                } else {
                    Color.White
                }

                val borderDp = if (it.enableCancel) 0.dp else 1.dp

                Box(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(containerColor, shape = RoundedCornerShape(12.dp))
                            .border(borderDp, Color.LightGray, shape = RoundedCornerShape(12.dp))
                    ) {
                        if (it.enableCancel) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 10.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(circleSize)
                                            .background(Color.White, shape = CircleShape)
                                            .border(1.dp, Color.Gray, shape = CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        val text = "${it.startTime} - ${it.endTime}"
                                        Text(text = text, color = Color.White)
                                        Text(
                                            text = "2024 / 02 / 01 (週四)",
                                            color = Color.LightGray
                                        )
                                    }
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                            ) {
                                Text(text = it.event?.name ?: "", color = Color.LightGray)
                                Text(text = it.event?.desc ?: "", color = Color.LightGray)
                            }
                        }
                    }
                }
            }
        }
    ) { measurable, constraints ->
        val placeable = measurable.mapIndexed { index, measurable ->
            val cardHeight = countTimeUnits(selectedTime[index], 30) * cardHeight.roundToPx()
            val cardConstraints =
                Constraints.fixed(width = cardWidth.roundToPx(), height = cardHeight)
            measurable.measure(cardConstraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeable.forEachIndexed { index, placeable ->
                val position =
                    timeList.indexOf(selectedTime[index].startTime) * cardHeight.roundToPx()
                placeable.placeRelative(x = 0, y = position)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateTimeList(start: String, end: String, intervalMinutes: Int): List<String> {
    val startTime = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm"))
    val endTime = LocalTime.parse(end, DateTimeFormatter.ofPattern("HH:mm"))
    val times = mutableListOf<String>()

    var currentTime = startTime
    while (currentTime <= endTime) {
        times.add(currentTime.format(DateTimeFormatter.ofPattern("HH:mm")))
        currentTime = currentTime.plusMinutes(intervalMinutes.toLong())
    }
    return times
}

@RequiresApi(Build.VERSION_CODES.O)
fun countTimeUnits(selectedTimes: SelectedTimeData, unitMinutes: Int): Int {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val start = LocalTime.parse(selectedTimes.startTime, formatter)
    val end = LocalTime.parse(selectedTimes.endTime, formatter)
    val duration = Duration.between(start, end).toMinutes().toInt()
    return duration / unitMinutes
}

@RequiresApi(Build.VERSION_CODES.O)
fun addOrMergeTimeSlot(
    inputTime: String,
    timeList: MutableList<SelectedTimeData>,
    unitMinutes: Int
) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val inputLocalTime = LocalTime.parse(inputTime, formatter)
    val newEndTime = inputLocalTime.plusMinutes(unitMinutes.toLong())

    // 检查是否有时间段已覆盖输入时间
    val isCovered = timeList.any { selectedTimeData ->
        val startTime = LocalTime.parse(selectedTimeData.startTime, formatter)
        val endTime = LocalTime.parse(selectedTimeData.endTime, formatter)
        inputLocalTime >= startTime && inputLocalTime < endTime && !selectedTimeData.enableCancel
    }

    if (!isCovered) {
        //先檢查點選的時間段是要取消還是新增, 如果overlapTimeSlot不為null, 表示有時間段重疊, 表示先前已有選取現在要執行取消的動作
        val overlapTimeSlot = timeList.find {
            val startTime = LocalTime.parse(it.startTime, formatter)
            val endTime = LocalTime.parse(it.endTime, formatter)
            inputLocalTime >= startTime && newEndTime <= endTime
        }

        if (overlapTimeSlot != null) {
            val startTime = LocalTime.parse(overlapTimeSlot.startTime, formatter)
            val endTime = LocalTime.parse(overlapTimeSlot.endTime, formatter)
            val isSame = inputLocalTime == startTime && newEndTime == endTime
            when {
                isSame -> {
                    timeList.remove(overlapTimeSlot)
                    return
                }

                inputLocalTime == startTime -> {
                    timeList.remove(overlapTimeSlot)
                    timeList.add(
                        SelectedTimeData(
                            startTime = newEndTime.format(formatter),
                            endTime = overlapTimeSlot.endTime,
                            event = null,
                            enableCancel = true
                        )
                    )
                    return
                }

                newEndTime == endTime -> {
                    timeList.remove(overlapTimeSlot)
                    val newSelectedTimeData = SelectedTimeData(
                        startTime = overlapTimeSlot.startTime,
                        endTime = inputTime,
                        event = null,
                        enableCancel = true
                    )
                    timeList.add(
                        newSelectedTimeData
                    )
                    return
                }

                else -> {
                    timeList.remove(overlapTimeSlot)
                    timeList.add(
                        SelectedTimeData(
                            startTime = overlapTimeSlot.startTime,
                            endTime = inputTime,
                            event = null,
                            enableCancel = true
                        )
                    )
                    timeList.add(
                        SelectedTimeData(
                            startTime = newEndTime.format(formatter),
                            endTime = overlapTimeSlot.endTime,
                            event = null,
                            enableCancel = true
                        )
                    )
                    return
                }
            }
        }

        // 檢查是否有結束時間與新增的起始時間相同
        val startMergeableSlot = timeList.find {
            val endTime = LocalTime.parse(it.endTime, formatter)
            it.enableCancel && endTime == inputLocalTime
        }

        val endMergeableSlot = timeList.find {
            val startTime = LocalTime.parse(it.startTime, formatter)
            it.enableCancel && newEndTime == startTime
        }
        var startTime = inputTime
        var endTime = newEndTime.format(formatter)
        when {
            startMergeableSlot != null && endMergeableSlot != null -> {
                startTime = startMergeableSlot.startTime
                endTime = endMergeableSlot.endTime
                timeList.remove(startMergeableSlot)
                timeList.remove(endMergeableSlot)
            }

            startMergeableSlot != null -> {
                startTime = startMergeableSlot.startTime
                timeList.remove(startMergeableSlot)
            }

            endMergeableSlot != null -> {
                endTime = endMergeableSlot.endTime
                timeList.remove(endMergeableSlot)
            }
        }

        timeList.add(
            SelectedTimeData(
                startTime = startTime,
                endTime = endTime,
                event = null,
                enableCancel = true
            )
        )
    }
}
