package com.project.booknes.controller;

import com.project.booknes.dto.ChangePasswordRequest;
import com.project.booknes.dto.UserRequestDTO;
import com.project.booknes.dto.UserResponseDTO;
import com.project.booknes.dto.UserUpdateDTO;
import com.project.booknes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {

        UserResponseDTO savedUser = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id,
                                             Principal connectedUser) {
        userService.deleteUser(id, connectedUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,
                                           @RequestBody @Valid UserUpdateDTO updatedUser,
                                                      Principal principal) {

        return new ResponseEntity<>(userService.updateUser(id, updatedUser, principal),
                                    HttpStatus.OK);
    }

    @PatchMapping("/me/change-password")
    public ResponseEntity<Void> changePassword (
            @RequestBody @Valid ChangePasswordRequest passwordRequest,
            Principal connectedUser) {

        userService.changePassword(passwordRequest, connectedUser);
        return ResponseEntity.noContent().build();
    }
}
