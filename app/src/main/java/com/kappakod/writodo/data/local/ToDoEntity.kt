package com.kappakod.writodo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val task: String,
    val headline: String,
    val date: String,
    val isCompleted: Boolean = false,
)








