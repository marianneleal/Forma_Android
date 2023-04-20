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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.forma.data.HomeViewModel
import com.example.forma.screens.Screen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = HomeViewModel(LocalContext.current)

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "My Habits") },
            backgroundColor = MaterialTheme.colors.primary
        )
        HabitList(viewModel = viewModel, navController = navController )
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HabitList(viewModel: HomeViewModel, navController: NavController) {
    val habits by viewModel.habits.collectAsState(emptyList())

    LazyColumn {
        items(habits.size) { index ->
            val habit = habits[index]
            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.deleteHabit(habit)
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
                    HabitRow(
                        habit = habit,
                        onClick = {
                            navController.navigate(Screen.DetailScreen.route + "/${habit.id}")
                        },

                        )
                })

        }
    }
}

