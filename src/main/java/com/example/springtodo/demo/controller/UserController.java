package com.example.springtodo.demo.controller;

import com.example.springtodo.demo.dto.ResponseDto;
import com.example.springtodo.demo.dto.UserDto;
import com.example.springtodo.demo.model.UserEntity;
import com.example.springtodo.demo.service.UserService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto)
    {

        try {
            UserEntity user = UserEntity.builder()
                    .email(userDto.getEmail())
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .build();

            UserEntity registerUser = userService.create(user);

            UserDto responseUserDto = UserDto.builder()
                    .email(registerUser.getEmail())
                    .id(registerUser.getId())
                    .username(registerUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(responseUserDto);
        }catch (Exception e)
        {
            ResponseDto<Object> responseDto = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDto);
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto)
    {
        UserEntity user = userService.getByCredentials(userDto.getEmail(), userDto.getPassword());

        if (user != null)
        {
            final UserDto responseUserDto = UserDto.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .build();

            return ResponseEntity.ok().body(responseUserDto);
        }
        else
        {
            ResponseDto responseDto = ResponseDto.builder().error("Login failed").build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

}
