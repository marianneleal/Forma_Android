package com.example.forma

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.forma.data.DetailViewModel
import com.example.forma.models.Habit
import com.example.forma.models.Task
import com.example.forma.screens.TaskRow
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(habitId: Long, navController: NavController) {
    val context = LocalContext.current

    val viewModel by remember {
        mutableStateOf(DetailViewModel(context, habitId))
    }

    if (viewModel.loading) {
        Text("Loading")
    } else {
        DetailScreenLoaded(viewModel, navController)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreenLoaded(viewModel: DetailViewModel, navController: NavController) {
    val tasks by viewModel.tasks.collectAsState(mutableListOf())
    var taskName by remember { mutableStateOf("") }
    val colorPickerController = rememberColorPickerController()
    var name by remember {
        mutableStateOf(viewModel.habit.name)
    }
    var colorPickerOpen by remember {
        mutableStateOf(false)
    }

    var selectedColor by remember { mutableStateOf( Color(viewModel.habit.color) ) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // store date
    val mDate = remember { mutableStateOf<Date?>(null) }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val datePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = Date(mYear, mMonth, mDayOfMonth)
        }, mYear, mMonth, mDay
    )
    var showAlertDialog by remember { mutableStateOf(false) }

    var toggleDatePicker by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                }
            },
            title = { Text(text = viewModel.habit.name) },
            backgroundColor = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        //Text(viewModel.habit.name, fontSize = 34.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Habit Information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = 8.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Card Title")
                TextField(
                    name, onValueChange = {
                        name = it
                    }, modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(0.8f)
                        .clip(RoundedCornerShape(20))
                        .border(1.dp, MaterialTheme.colors.onSurface, RoundedCornerShape(20))
                        .clickable {
                            colorPickerOpen = !colorPickerOpen
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        if (colorPickerOpen) {
                            Text(
                                text = "Click to save Color",
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Text(
                                text = "Color",
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(50))
                                .background(selectedColor ?: Color.LightGray)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(1.0f)
                        .clip(RoundedCornerShape(20))
                        .border(1.dp, MaterialTheme.colors.onSurface, RoundedCornerShape(20))
                        .clickable {
                            datePickerDialog.show()
                        }
                ) {
                    if (mDate.value == null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            Text("No Due Date", modifier = Modifier.padding(5.dp))
                            Switch(
                                checked = toggleDatePicker,
                                onCheckedChange = {
                                    toggleDatePicker = it
                                    datePickerDialog.show()
                                },
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else {
                        val dateFormatter = SimpleDateFormat("dd MMMM", Locale.getDefault())
                        val formattedDate: String = mDate.value?.let { dateFormatter.format(it) } ?: "Select a date"
                        Column() {
                            Text(
                                text = " Due Date: \n $formattedDate",
                                modifier = Modifier.padding(10.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
                                    .fillMaxWidth(0.8f)
                                    .clip(RoundedCornerShape(20))

                                    .clickable {
                                        // mDate.value = null
                                        toggleDatePicker = false
                                    }

                            ) {
                                Text("Remove Due Date", modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        mDate.value = null
                                        toggleDatePicker = false
                                    })

                            }
                        }
                    }
                }
            }
            if (colorPickerOpen) {
                Column() {

                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                        .padding(10.dp),

                    controller = colorPickerController,
                    onColorChanged = { colorEnvelope ->
                        selectedColor = Color(colorEnvelope.color.toArgb())


                    }
                )
                Button(onClick = { colorPickerOpen = false }) {
                    Text("Save Color")
                }
            }
            }

        }
        Text("Tasks", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = 8.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Button(
                    onClick = {
                                showAlertDialog = true
                              // add task to habit
                              // display task in list
                    },

                )
                {
                    Text("Add Task")
                }
                if (showAlertDialog){
                    AlertDialog(
                        onDismissRequest = { },
                        title = { Text("Add Task") },
                        text = {
                            TextField(
                                value = taskName,
                                onValueChange = { taskName = it },
                                label = { Text("Task Name") }
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                        viewModel.addTask(taskName)
                                        showAlertDialog = false

                                    taskName = ""
                                }
                            ) {
                                Text("Add")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showAlertDialog = false
                                }
                            ) {
                                Text("Cancel")
                            }
                        }
                    )


                }
                LazyColumn() {
                    items(count = tasks.size, key = {index -> tasks[index].hashCode() } ) { index ->
                        val task = tasks[index]

                        val dismissState = rememberDismissState()
                        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                            viewModel.deleteTask(task)
                        }
                        SwipeToDismiss(
                            state = dismissState,
                            modifier = Modifier
                                .padding(vertical = Dp(1f)),
                            directions = setOf(
                                DismissDirection.EndToStart
                            ),
                            dismissThresholds = { direction ->
                                FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
                            },
                            background = {
                                val color by animateColorAsState(
                                    when (dismissState.targetValue) {
                                        DismissValue.Default -> Color.White
                                        else -> Color.Red
                                    }
                                )
                                val alignment = Alignment.CenterEnd
                                val icon = Icons.Default.Delete

                                val scale by animateFloatAsState(
                                    if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                                )

                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(horizontal = Dp(20f)),
                                    contentAlignment = alignment
                                ) {
                                    Icon(
                                        icon,
                                        contentDescription = "Delete Icon",
                                        modifier = Modifier.scale(scale)
                                    )
                                }
                            },
                            dismissContent = {
                                TaskRow(task = task) {
                                    viewModel.updateTask(task)
                                }
                            })
                    }
                }
            }
        }
        Button(
            onClick = {
                viewModel.habit.name = name
                viewModel.habit.color = selectedColor.toArgb()
                viewModel.viewModelScope.launch {
                    viewModel.save()
                    navController.navigateUp()
                }
            },
            modifier = Modifier.align(Alignment.End)
        )
        {
            Text("Save Habit")

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreen(-1, NavController(LocalContext.current))
}