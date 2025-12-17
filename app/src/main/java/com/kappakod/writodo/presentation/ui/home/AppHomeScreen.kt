package com.kappakod.writodo.presentation.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kappakod.writodo.R.drawable
import com.kappakod.writodo.R.string
import com.kappakod.writodo.data.local.TodoDatabase
import com.kappakod.writodo.presentation.ui.theme.KBlue20
import com.kappakod.writodo.presentation.ui.theme.KGrey00
import com.kappakod.writodo.presentation.ui.theme.KGrey10
import com.kappakod.writodo.presentation.ui.theme.KGrey20
import com.kappakod.writodo.presentation.ui.theme.KRed10
import com.kappakod.writodo.presentation.ui.theme.KWhite
import com.kappakod.writodo.presentation.ui.todo_list.ToDoItem
import com.kappakod.writodo.presentation.viewmodel.TodoViewModel
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHomeScreen() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val viewModel =
        TodoViewModel(todoDao = TodoDatabase.getDatabase(context = LocalContext.current).todoDao())
  val todos = viewModel.todos.collectAsState().value

    val toastList: List<String> = listOf(
        stringResource(string.enjoy),
        stringResource(string.keep_going),
        stringResource(string.add_a_new_todo)
    )

    val calendar = Calendar.getInstance().time
    val dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(calendar).toString()
    val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar).toString()
    val date =  "$dateFormat - $timeFormat"



    Scaffold(
        containerColor = Color.Black,
        contentColor = Color.White,
        modifier = Modifier.padding(bottom = 32.dp, top = 16.dp),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null

                    ) {
                        Toast
                            .makeText(context, toastList.random(), Toast.LENGTH_SHORT)
                            .show()
                    },
                horizontalArrangement = Arrangement.Absolute.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = drawable.headicon),
                    contentDescription = "logo",
                    alignment = Alignment.Center
                )

            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
                    scope.launch {
                        sheetState.show()
                    }

                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .size(48.dp)
                    .absoluteOffset(y = (+12).dp),

                containerColor = KGrey00,
            ) {
                Image(
                    painter = painterResource(id = drawable.add),
                    contentDescription = "add",
                    modifier = Modifier.size(
                        16.dp
                    )
                )


            }

        },
        floatingActionButtonPosition = FabPosition.End


    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .imePadding()
                    .padding(vertical = 8.dp, horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(todos.size) { index ->
                        ToDoItem(todo = todos[index])
                    }
                }

            }

        }


    }



    if (showBottomSheet) {
        var task: String by rememberSaveable { mutableStateOf("") }
        var headline: String by rememberSaveable { mutableStateOf("") }
        val toastText = stringResource(string.empty_field)
        val maxCharForHeadline = 40
        ModalBottomSheet(
            containerColor = Color(0xFF141414),
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            modifier = Modifier.padding(top = 48.dp),
            properties = ModalBottomSheetProperties(
                shouldDismissOnBackPress = true,
            ),
            scrimColor = Color(0x80000000),

            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = headline,
                    onValueChange = {  if (it.length <= maxCharForHeadline) headline = it },
                    placeholder = { Text(text = stringResource(string.headline)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    shape = RoundedCornerShape(32.dp),
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = KGrey10,
                        unfocusedContainerColor = KGrey10,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Color.White,
                        focusedLabelColor = KBlue20,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = KBlue20,
                        focusedPlaceholderColor = KGrey00,
                        unfocusedPlaceholderColor = KGrey00,
                        selectionColors = TextSelectionColors(
                            handleColor = KBlue20,
                            backgroundColor = KBlue20.copy(alpha = 0.4f)
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                TextField(
                    value = task,
                    onValueChange = { task = it },
                    placeholder = { Text(text = stringResource(string.add_a_new_todo)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    shape = RoundedCornerShape(32.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = KGrey10,
                        unfocusedContainerColor = KGrey10,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Color.White,
                        focusedLabelColor = KBlue20,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = KBlue20,
                        focusedPlaceholderColor = KGrey00,
                        unfocusedPlaceholderColor = KGrey00,
                        selectionColors = TextSelectionColors(
                            handleColor = KBlue20,
                            backgroundColor = KBlue20.copy(alpha = 0.4f)
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            showBottomSheet = false
                            scope.launch {
                                sheetState.hide()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = KRed10, containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = stringResource(string.cancel),
                            fontWeight = FontWeight.Bold,
                            color = KRed10
                        )
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    TextButton(
                        onClick = {
                            if (task.isNotEmpty() && headline.isNotEmpty()) {
                                scope.launch {
                                    viewModel.addTodo(task, headline, date)
                                    task = ""
                                    headline = ""
                                }
                                showBottomSheet = false
                                scope.launch {
                                    sheetState.hide()
                                }
                            } else {
                                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                            }


                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = KBlue20,
                            containerColor = Color.Transparent
                        ),

                        ) {
                        Text(text = stringResource(string.save), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }


}



