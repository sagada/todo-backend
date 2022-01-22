package com.example.springtodo.demo.service;

import com.example.springtodo.demo.model.TodoEntity;
import com.example.springtodo.demo.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public String testService()
    {
        TodoEntity todoEntity = TodoEntity.builder().title("My first").build();
        todoRepository.save(todoEntity);

        return todoRepository.getById(todoEntity.getId()).getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity)
    {
        validate(entity);
        todoRepository.save(entity);
        return todoRepository.findByUserId(entity.getUserId());
    }

    private void validate(final TodoEntity todoEntity)
    {
        if (todoEntity == null)
        {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null!");
        }

        if (todoEntity.getUserId() == null)
        {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    public List<TodoEntity> retrieve(String temporaryUserId)
    {
        return todoRepository.findByUserId(temporaryUserId);
    }

    public List<TodoEntity> update(final TodoEntity entity)
    {
        validate(entity);

        final Optional<TodoEntity> original = todoRepository.findById(entity.getId());

        original.ifPresent(todo ->
        {
            log.info("entity : {}", todo);
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            todoRepository.save(todo);
        });

        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete(TodoEntity entity) {
        validate(entity);

        try{
            todoRepository.delete(entity);
        }catch (Exception e)
        {
            log.error("error deleting entity {}, {}", entity.getId(), e);
            throw new RuntimeException("error deleting entity " + entity.getId());
        }

        return retrieve(entity.getUserId());
    }
}
