package com.example.forma
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.forma.viewmodels.DetailViewModel
import com.example.forma.screens.DetailScreenLoaded
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(habitId: Long, navController: NavController) {
    val context = LocalContext.current
    val viewModel by remember {
        mutableStateOf(DetailViewModel(context, habitId))
    }

    if (viewModel.loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp),
                strokeWidth = 4.dp,
                color = MaterialTheme.colors.primary
            )
            Text("Loading")
        }
    } else {
        DetailScreenLoaded(viewModel, navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreen(-1, NavController(LocalContext.current))
}