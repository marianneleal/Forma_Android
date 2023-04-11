package com.example.forma

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(habit: Habit?, navController: NavController) {
    val colorPickerController = rememberColorPickerController()
    var name by remember {
        mutableStateOf("Habit Name")
    }
    var colorPickerOpen by remember {
        mutableStateOf(false)
    }
    var datePickerOpen by remember {
        mutableStateOf(true)
    }
    var selectedColor by remember { mutableStateOf(Color.LightGray) }
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

    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    var toggleDatePicker by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text("Habit Name Here When Created", fontSize = 34.sp, fontWeight = FontWeight.Bold)
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
                                .background(selectedColor)
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
                            mDatePickerDialog.show()
                        }
                ) {if (mDate.value == "") {
                    Row( verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)){
                    Text("No Due Date", modifier = Modifier.padding(5.dp))
                    Switch(
                        checked = toggleDatePicker,
                        onCheckedChange = {
                            toggleDatePicker = it
                            mDatePickerDialog.show()
                        },
                        modifier = Modifier.padding(16.dp)
                    )
                }
                } else {
                    Column() {
                        Text(text = "Due Date: ${mDate.value}", modifier = Modifier.padding(10.dp))
                        Box(
                            modifier = Modifier
                                .padding(top = 16.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
                                .fillMaxWidth(0.8f)
                                .clip(RoundedCornerShape(20))

                                .clickable {
                                    mDate.value = ""
                                    toggleDatePicker = false
                                }

                        ) {
                            Text("Remove Due Date", modifier = Modifier.padding(10.dp))
                        }
                    }
                }
                }
            }
        }
        if (colorPickerOpen) {
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
        }
        Spacer(modifier = Modifier.height(16.dp))
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
                Row() {

                    Button(onClick = { /*TODO*/ },)
                    {
                        Text("Add Task")
                    }
                }

            }
            }


        Button(onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.End)
        )
        {
            Text("Save Habit")
            
        }
    }
}