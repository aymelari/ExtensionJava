package org.example.myextension.controller;

import lombok.RequiredArgsConstructor;
import org.example.myextension.dto.UserRequestDto;
import org.example.myextension.dto.UserResponseDto;
import org.example.myextension.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;



    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
      return  ResponseEntity.ok(userService.findUserById(id));
    }
    /*@GetMapping("/get/{id}")
    public ResponseEntity<UserResponseDto> findUserByName(@PathVariable String name) {

        return ResponseEntity.ok(userService.findUserByName(name));
    }*/




}
