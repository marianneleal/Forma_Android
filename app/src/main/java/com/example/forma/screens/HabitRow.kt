package com.example.forma
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.example.forma.models.Habit
import com.example.forma.models.Task
import java.util.*


@Composable
fun HabitRow(habit: Habit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(color = Color(habit.color), shape = RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = habit.name, style = MaterialTheme.typography.h6)
        }
    }
}

@Preview
@Composable
fun HabitRowPreview() {
    val habit = Habit("Sample Habit", Color.Blue.toArgb(),
        Date(),listOf(Task(0,"bla", false,), Task(1,"blabla", true)))
    HabitRow(habit = habit, onClick = {})
}
