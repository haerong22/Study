package org.example.todojavatokotlin.service

import org.example.todojavatokotlin.api.model.TodoRequest
import org.example.todojavatokotlin.domain.Todo
import org.example.todojavatokotlin.domain.TodoRepository
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.domain.Sort.by
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class TodoService(
    private val todoRepository: TodoRepository,
) {

    @Transactional(readOnly = true)
    fun findAll(): List<Todo> =
        todoRepository.findAll(by(Direction.DESC, "id"))

    @Transactional(readOnly = true)
    fun findById(id: Long): Todo =
        todoRepository.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @Transactional
    fun create(request: TodoRequest?): Todo {
        checkNotNull(request) {"TodoRequest is null"}

        val todo = Todo(
            title = request.title,
            description = request.description,
            done = request.done,
            createdAt = LocalDateTime.now(),
        )

        return todoRepository.save(todo)
    }

    @Transactional
    fun update(id: Long, request: TodoRequest?): Todo {
        checkNotNull(request) {"TodoRequest is null"}

        return findById(id).let {
            it.update(
                title = request.title,
                description = request.description,
                done = request.done,
            )
            todoRepository.save(it)
        }
    }

    fun delete(id: Long) = todoRepository.deleteById(id)
}