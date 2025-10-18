package com.example.id_validate.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ValidateRequestDTO {
    @NotBlank
    private String type;   // PAN or AADHAAR
    @NotBlank
    private String value;  // PAN or Aadhaar number
}
