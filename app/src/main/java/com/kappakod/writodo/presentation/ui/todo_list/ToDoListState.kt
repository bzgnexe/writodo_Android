package com.kappakod.writodo.presentation.ui.todo_list

import com.kappakod.writodo.data.local.TodoEntity

data class ToDoListState(
    val toDos: List<TodoEntity> = emptyList(),
    val isAddingToDo: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDeleted: Boolean = false,
)