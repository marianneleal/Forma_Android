package com.example.forma

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(viewModel: HabitViewModel, navController: NavController) {
    val habits = remember { mutableStateOf(emptyList<Habit>()) }
    LaunchedEffect(true) {
        habits.value = viewModel.habits.value
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Habits") },
            backgroundColor = MaterialTheme.colors.primary
        )
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(count = habits.value.size) { index ->
                HabitRow(
                    habit = habits.value[index],
                    onClick = {
                        navController.navigate(Screen.DetailScreen.route + "/${habits.value[index].id}")
                    }

                )
            }
        }
        HabitList(habitViewModel = viewModel, navController = navController)
        FloatingActionButton(
            onClick = {
                // navController.navigate(Screen.DetailScreen.route)
                navController.navigate(Screen.DetailScreen.withArgs("0"))

            }
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Habit")
        }

    }
}

@Composable
private fun HabitList(habitViewModel: HabitViewModel, navController: NavController) {
    val habits by habitViewModel.habits.collectAsState(emptyList())
    LazyColumn {
        items(habits.size) { habit ->
            HabitRow(habit = habits[habit], onClick = {
                navController.navigate(Screen.DetailScreen.route)
            })
        }
    }
}

