package com.kappakod.writodo.presentation.ui.todo_list

import android.media.MediaPlayer
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappakod.writodo.R
import com.kappakod.writodo.data.local.TodoDatabase
import com.kappakod.writodo.data.local.TodoEntity
import com.kappakod.writodo.presentation.ui.theme.KGreen10
import com.kappakod.writodo.presentation.ui.theme.KGrey00
import com.kappakod.writodo.presentation.ui.theme.KGrey10
import com.kappakod.writodo.presentation.ui.theme.KGrey20
import com.kappakod.writodo.presentation.ui.theme.KWhite
import com.kappakod.writodo.presentation.viewmodel.TodoViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoItem(
    todo: TodoEntity,
) {
    val todoDao = TodoDatabase.getDatabase(context = LocalContext.current).todoDao()
    val isCompleted = TodoViewModel(todoDao = todoDao)
    val context = LocalContext.current
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()
    var visible by remember {
        mutableStateOf(true)
    }
    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0f,
        label = "alpha"
    )
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = KGrey20,
            contentColor = Color.White
        ),
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentHeight()
                        .fillMaxSize(0.8f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = todo.date,
                            fontSize = 12.sp,
                            color = KGrey00
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = todo.headline,
                        fontWeight = FontWeight.Bold,
                        fontSize =  18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(KGrey10)

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = todo.task,
                        fontWeight = FontWeight.Normal
                    )
                }
                Column(
                    modifier = Modifier.wrapContentSize(), verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            isCompleted.deleteTodo(todo.copy(isCompleted = !todo.isCompleted))
                            mediaPlayer = MediaPlayer.create(context, R.raw.done)
                            mediaPlayer?.start()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = KWhite
                        )
                    }
                }
            }
        }
    }
}



