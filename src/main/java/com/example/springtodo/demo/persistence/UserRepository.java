package com.example.springtodo.demo.persistence;

import com.example.springtodo.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Boolean existsByEmail(String email);
    UserEntity findByEmailAndPassword(String email, String password);
    UserEntity findByEmail(String email);
}
