package com.example.forma

import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import androidx.room.Room
import com.example.forma.data.HabitDatabase
import com.example.forma.data.Repository
import com.example.forma.ui.theme.FormaTheme


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            HabitDatabase::class.java,
            "HabitDatabase"
        ).build()
        val repository = Repository(applicationContext as Application)
        setContent {
            FormaTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)

            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        FormaTheme {
            Text("hello world")
        }
    }
}