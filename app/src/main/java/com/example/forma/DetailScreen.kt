package com.example.forma

import android.content.res.Resources
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
import androidx.navigation.NavController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun DetailScreen(habitId: Int? = null, navController: NavController) {
    val colorPickerController = rememberColorPickerController()
    var name by remember {
        mutableStateOf("Habit Name")
    }
    var colorPickerOpen by remember {
        mutableStateOf(false)
    }
    var selectedColor by remember { mutableStateOf(Color.Red) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        TextField(name, onValueChange = {
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
                    colorPickerOpen = true
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(20))
                        .background(selectedColor)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Select Color")
            }
        }
if (colorPickerOpen) {
    HsvColorPicker(

        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(10.dp),

        controller = colorPickerController,
        onColorChanged = {}
    )
        }
        Button(onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.End))
        {
            Text("Save")
            
        }
    }
}