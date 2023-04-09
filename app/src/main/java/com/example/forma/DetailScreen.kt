package com.example.forma

import android.content.res.Resources
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.forma.ui.theme.Form
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(habitId: Int? = null, navController: NavController) {
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

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text("Habit Name Here When Created", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
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
                Text("Habit Info")
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
            }
            DatePicker()
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


        Button(onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.End))
        {
            Text("Save")
            
        }
    }
}