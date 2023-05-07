package com.example.forma

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.forma.viewmodels.HomeViewModel
import com.example.forma.screens.Screen
import com.example.forma.screens.HabitList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = HomeViewModel(LocalContext.current)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Habits") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

            HabitList(viewModel = viewModel, navController = navController)

            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.DetailScreen.withArgs("0")) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Habit")
            }
        }
        },
    )
}

