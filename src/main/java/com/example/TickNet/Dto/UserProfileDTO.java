package com.example.TickNet.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserProfileDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
}
