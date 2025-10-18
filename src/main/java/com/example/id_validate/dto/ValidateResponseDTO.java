package com.example.id_validate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidateResponseDTO {
    private boolean valid;
    private String normalized;
    private String message;
    private String id;
}
