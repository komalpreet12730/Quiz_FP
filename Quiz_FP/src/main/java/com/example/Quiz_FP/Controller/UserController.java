package com.example.Quiz_FP.Controller;

import com.example.Quiz_FP.model.User;
import com.example.Quiz_FP.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        boolean result = userService.updateUser(id, updatedUser);
        return result
                ? ResponseEntity.ok("User updated successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}

