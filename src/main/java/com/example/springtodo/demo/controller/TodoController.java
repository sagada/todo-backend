package com.example.springtodo.demo.controller;

import com.example.springtodo.demo.dto.ResponseDto;
import com.example.springtodo.demo.dto.TodoDto;
import com.example.springtodo.demo.model.TodoEntity;
import com.example.springtodo.demo.service.TodoService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService)
    {
        this.todoService = todoService;
    }

    @GetMapping("/test")
    public ResponseEntity<?> testTodo()
    {
        String str = todoService.testService();

        List<String> list = Lists.newArrayList(str);

        ResponseDto<String> responseDto = ResponseDto.<String>builder().data(list).build();

        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDto dto)
    {
        try {
            String temporaryUserId = "temporary-user";

            TodoEntity entity = TodoDto.toEntity(dto);

            entity.setId(null);

            entity.setUserId(temporaryUserId);

            List<TodoEntity> entities = todoService.create(entity);

            List<TodoDto> todoDtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

            ResponseDto<TodoDto> responseDto = ResponseDto.<TodoDto>builder().data(todoDtos).build();

            return ResponseEntity.ok().body(responseDto);
        }catch (Exception e)
        {
            String error = e.getMessage();
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList()
    {
        String temporaryUserId = "temporary-user";
        List<TodoEntity> todoEntities = todoService.retrieve(temporaryUserId);
        List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDto<TodoDto> responseDto = ResponseDto.<TodoDto>builder().data(todoDtos).build();
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDto dto)
    {
        String temporaryUserId = "temporary-user";
        TodoEntity entity = TodoDto.toEntity(dto);
        entity.setUserId(temporaryUserId);

        List<TodoEntity> todoEntities = todoService.update(entity);

        List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDto<TodoDto> responseDto = ResponseDto.<TodoDto>builder().data(todoDtos).build();
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDto dto)
    {
        try {
            String temporaryUserId = "temporary-user";
            TodoEntity entity = TodoDto.toEntity(dto);
            entity.setUserId(temporaryUserId);

            List<TodoEntity> todoEntities = todoService.delete(entity);

            List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());
            ResponseDto<TodoDto> responseDto = ResponseDto.<TodoDto>builder().data(todoDtos).build();
            return ResponseEntity.ok().body(responseDto);
        }
        catch (Exception e)
        {
            String error = e.getMessage();
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
