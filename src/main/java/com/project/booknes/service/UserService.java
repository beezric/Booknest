package com.project.booknes.service;

import com.project.booknes.dto.ChangePasswordRequest;
import com.project.booknes.dto.UserRequestDTO;
import com.project.booknes.dto.UserResponseDTO;
import com.project.booknes.dto.UserUpdateDTO;
import com.project.booknes.enums.Role;
import com.project.booknes.exception.ResourceDuplicationException;
import com.project.booknes.exception.ResourceNotFoundException;
import com.project.booknes.mapper.UserMapper;
import com.project.booknes.model.User;
import com.project.booknes.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> getAllUsers() {
        return this.userRepository.findAll().stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User By ID: " + id + " does not exist"));

        return userMapper.toResponseDTO(user);
    }


    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {


        if(userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new ResourceDuplicationException("User with username: " + userRequestDTO.getUsername()
            + " already taken");
        }

        if(userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResourceDuplicationException("Email: " + userRequestDTO.getEmail()
                    + " already taken");
        }

        User newUser = userMapper.toEntity(userRequestDTO);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));


        User savedUser = userRepository.save(newUser);
        return userMapper.toResponseDTO(savedUser);
    }

    public void deleteUser(Long id, Principal connectedUser) {

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with ID: " + id + " does not exist");
        }

        User authenticatedUser = userRepository.findByUsername(connectedUser.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));


        if(!authenticatedUser.getId().equals(id) && !authenticatedUser.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("You can only delete your account");
        }

        userRepository.deleteById(id);
    }

    public UserResponseDTO updateUser(Long id, UserUpdateDTO updatedUserDTO, Principal connectedUser) {

        User authenticatedUser = userRepository.findByUsername(connectedUser.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User with username: " +
                        connectedUser.getName() + " does not exist"));

        if(!authenticatedUser.getId().equals(id) && !authenticatedUser.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("You can only update your own account");
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + id + " does not exist"));


        userMapper.updateUserFromDTO(updatedUserDTO, existingUser);
        User savedUser = userRepository.save(existingUser);
        return userMapper.toResponseDTO(savedUser);
    }

    public void changePassword(ChangePasswordRequest passwordRequest, Principal connectedUser) {

        User user = userRepository.findByUsername(connectedUser.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //check if the current password is correct
        if(!passwordEncoder.matches(passwordRequest.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Wrong current password");
        }

        //check if the two new passwords are the same
        if(!passwordRequest.getNewPassword().equals(passwordRequest.getConfirmationPassword())) {
            throw new IllegalArgumentException("Confirmation Password does not match new password.");
        }

        //updating the password
        user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));

        //saving the new password
        userRepository.save(user);
    }

}
