package org.example.todojavatokotlin.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.example.todojavatokotlin.domain.Todo

data class TodoListResponse(
    val items: List<TodoResponse>,
) {

    val size: Int
        @JsonIgnore
        get() = items.size

    fun get(index: Int) = items[index]

    companion object {

        @JvmStatic
        fun of(todoList: List<Todo>) =
            TodoListResponse(todoList.map(TodoResponse::of))
    }
}