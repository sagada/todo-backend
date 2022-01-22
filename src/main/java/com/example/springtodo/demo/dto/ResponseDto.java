package com.example.springtodo.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto<T>{
    private String error;
    private List<T> data;
}
