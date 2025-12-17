package com.kappakod.writodo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappakod.writodo.data.local.TodoDao
import com.kappakod.writodo.data.local.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class TodoViewModel(private val todoDao: TodoDao) : ViewModel() {
    private val _todos = MutableStateFlow<List<TodoEntity>>(emptyList())
    val todos: StateFlow<List<TodoEntity>> = _todos.asStateFlow()

    init {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                todoDao.getAllTodos().collect {
                    _todos.value = it
                }
            }
        }
    }

    fun addTodo(task : String, headline : String, date : String ) {
        viewModelScope.launch {
            todoDao.insert(TodoEntity( task = task, headline = headline, date = date))
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch {
            todoDao.delete(todo)
        }
    }
}