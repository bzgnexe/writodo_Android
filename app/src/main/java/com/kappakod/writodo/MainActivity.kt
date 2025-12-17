package com.kappakod.writodo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kappakod.writodo.presentation.navigation.AppNavGraph
import com.kappakod.writodo.presentation.ui.theme.WritodoTheme


class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WritodoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black)
                                .padding(16.dp)
                        ) {
                            navController = rememberNavController()
                            AppNavGraph(navController = navController)
                        }
                    }
                }
            }
        }
    }



