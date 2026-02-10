package com.example.TickNet.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProfileDTO {
    @NotBlank
    private String name;

    // Phone can be optional or required depending on rules, user asked for
    // "Required fields" in generic list but typically phone is optional.
    // However, prompts says "Proper validation: Required fields". I'll assume Name
    // is required. Phone might be optional but validation usually applies if
    // present.
    // Let's stick to Name required.
    private String phone;
}
