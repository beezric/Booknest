package com.project.booknes.dto;



import com.project.booknes.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
}
