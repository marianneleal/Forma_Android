package com.example.forma

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DetailScreen(habitId: Int? = null, navController: NavController) {
    var name by remember {
        mutableStateOf("insert real habit here")
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text("detail screen this text should show up on the detail screen.")
        //TextField(name, onValueChange = {
       //     name = it
       // }, modifier = Modifier.fillMaxWidth()
       // )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.End))
        {
            Text("Save")
            
        }
    }
}