package com.project.booknes.mapper;

import com.project.booknes.dto.UserRequestDTO;
import com.project.booknes.dto.UserResponseDTO;
import com.project.booknes.dto.UserUpdateDTO;
import com.project.booknes.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {



    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }


    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt());
    }

    public void updateUserFromDTO(UserUpdateDTO dto, User user) {

        if(dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }

        if(dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

    }


}
