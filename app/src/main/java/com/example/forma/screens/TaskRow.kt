package com.example.forma.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.forma.models.Task

@Composable
fun TaskRow(task: Task, onClick: () -> Unit) {
    var completed by remember { mutableStateOf(false) }

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
            Checkbox(checked = completed, onCheckedChange = { completed = it })
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = task.name, style = MaterialTheme.typography.h6)
        }
    }
}