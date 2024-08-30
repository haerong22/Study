package org.example.todojavatokotlin.service;

import org.example.todojavatokotlin.api.model.TodoRequest;
import org.example.todojavatokotlin.domain.Todo;
import org.example.todojavatokotlin.domain.TodoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional(readOnly = true)
    public List<Todo> findAll() {
        return todoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional(readOnly = true)
    public Todo findById(Long id) {
        return todoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Todo create(TodoRequest request) {
        Assert.notNull(request, "TodoRequest is null");

        Todo todo = new Todo(
                null,
                request.getTitle(),
                request.getDescription(),
                false,
                LocalDateTime.now(),
                null
        );
        return todoRepository.save(todo);
    }

    @Transactional
    public Todo update(Long id, TodoRequest request) {
        Assert.notNull(request, "TodoRequest is null");

        Todo todo = findById(id);
        todo.update(request.getTitle(),
                    request.getDescription(),
                    request.getDone());
        return todoRepository.save(todo);
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }
}