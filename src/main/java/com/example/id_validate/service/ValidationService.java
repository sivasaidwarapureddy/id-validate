package com.example.id_validate.service;

import java.time.Instant;
import org.springframework.stereotype.Service;
import com.example.id_validate.dto.ValidateRequestDTO;
import com.example.id_validate.dto.ValidateResponseDTO;
import com.example.id_validate.model.ValidationRecord;
import com.example.id_validate.repo.ValidationRecordRepository;
import com.example.id_validate.util.AadhaarVerhoeff;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final ValidationRecordRepository repo;
    private static final String PAN_REGEX = "^[A-Z]{5}[0-9]{4}[A-Z]$";

    public ValidateResponseDTO validate(ValidateRequestDTO req) {
        String type = req.getType().trim().toUpperCase();
        String raw = req.getValue();

        boolean valid;
        String normalized;
        String message;

        switch (type) {
            case "PAN" -> {
                normalized = raw.toUpperCase().replaceAll("\\s+", "");
                valid = normalized.matches(PAN_REGEX);
                message = valid ? "Valid PAN format." : "Invalid PAN format.";
            }
            case "AADHAAR", "AADHAR" -> {
                normalized = raw.replaceAll("\\D", "");
                boolean lengthOk = normalized.matches("\\d{12}");
                boolean checksumOk = lengthOk && AadhaarVerhoeff.isValid(normalized);
                valid = lengthOk && checksumOk;
                message = valid ? "Valid Aadhaar number." :
                        (lengthOk ? "Invalid Aadhaar checksum." : "Aadhaar must be 12 digits.");
                type = "AADHAAR";
            }
            default -> throw new IllegalArgumentException("Unsupported type. Use PAN or AADHAAR.");
        }

        ValidationRecord saved = repo.save(
            ValidationRecord.builder()
                .type(type)
                .input(raw)
                .normalized(normalized)
                .valid(valid)
                .message(message)
                .createdAt(Instant.now())
                .build()
        );

        return new ValidateResponseDTO(valid, normalized, message, saved.getId());
    }
}
