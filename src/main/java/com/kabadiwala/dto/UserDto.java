package com.kabadiwala.dto;

import lombok.Data;
import com.kabadiwala.enums.Role;
import com.kabadiwala.enums.Status;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String avatarUrl;
    private String contactNumber;
    private Role role;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}