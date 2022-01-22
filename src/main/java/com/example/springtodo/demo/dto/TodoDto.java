package com.example.springtodo.demo.dto;

import com.example.springtodo.demo.model.TodoEntity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDto {
    private String id;
    private String title;
    private boolean done;

    public TodoDto(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    public static TodoEntity toEntity(final TodoDto todoDto)
    {
        return TodoEntity.builder()
                .id(todoDto.getId())
                .title(todoDto.getTitle())
                .done(todoDto.isDone())
                .build();
    }
}
