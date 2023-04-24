package com.example.forma.screens

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.forma.R
import com.example.forma.data.DetailViewModel
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


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

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()
    mCalendar.time = Date()

    // store date
    val mDate = remember { mutableStateOf<Date?>(viewModel.habit.dueDate) }

//     Declaring DatePickerDialog and setting
//     initial values as current values (present year, month and day)
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = Date(mYear, mMonth, mDayOfMonth)
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)
    )

    var showAlertDialog by remember { mutableStateOf(false) }

    Scaffold(
        content = { padding ->
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
        Text(
            "HABIT INFO",
            fontSize = 15.sp,
            style = MaterialTheme.typography.h6.copy(color = Color.Gray),
            modifier = Modifier.padding(start = 16.dp, top = 30.dp, bottom = 5.dp)
        )
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
                TextField(
                    name, onValueChange = {
                        name = it
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clickable {
                            colorPickerOpen = !colorPickerOpen
                        },
                    elevation = 4.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Color",
                            modifier = Modifier.weight(1f)
                        )

                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(50))
                                .background(selectedColor ?: Color.LightGray)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clickable {
                            datePickerDialog.show()
                        },
                    elevation = 4.dp
                ) {
                    val dateFormatter = SimpleDateFormat("dd MMMM", Locale.getDefault())
                    val formattedDate: String = mDate.value?.let { "Date: ${dateFormatter.format(it)}" } ?: "No Due Date"

                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp, horizontal = 16.dp)
                    ) {
                        Text(formattedDate, modifier = Modifier.padding(5.dp))
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = mDate.value != null,
                            onCheckedChange = {
                                  if (mDate.value == null) {
                                        datePickerDialog.show()
                                    } else {
                                        mDate.value = null
                                  }
                            },
                        )
                    }
                }
            }
            if (colorPickerOpen) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    HsvColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),

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
        Text(
            "TASKS", fontSize = 15.sp, style = MaterialTheme.typography.h6.copy(color = Color.Gray),
            modifier = Modifier.padding(start = 16.dp)
        )
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
                if (showAlertDialog) {
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
                                onClick = {
                                    showAlertDialog = false
                                }
                            ) {
                                Text("Cancel")
                            }
                        }
                    )


                }
                LazyColumn() {
                    items(count = tasks.size, key = { index -> tasks[index].hashCode() }) { index ->
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 16.dp)
                        .clickable(
                            onClick = {
                                showAlertDialog = true
                            })
                )
                {
                    Icon(
                        Icons.Filled.AddCircle,
                        contentDescription = "Add Task",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Task")
                }
            }
        }

        }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                FloatingActionButton(

                    onClick = {
                        viewModel.habit.name = name
                        viewModel.habit.color = selectedColor.toArgb()
                        viewModel.habit.dueDate = mDate.value
                        viewModel.viewModelScope.launch {
                            viewModel.save()
                            navController.navigateUp()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.icon_save), contentDescription = "Save")
                }
            }
    },
    )
}


