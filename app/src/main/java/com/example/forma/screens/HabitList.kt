package com.example.forma.screens
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.example.forma.HabitRow
import com.example.forma.viewmodels.HomeViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HabitList(viewModel: HomeViewModel, navController: NavController) {
    val habits by viewModel.habits.collectAsState(emptyList())

    LazyColumn {
        items(habits.size, key = {index -> habits[index].id}) { index ->
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
