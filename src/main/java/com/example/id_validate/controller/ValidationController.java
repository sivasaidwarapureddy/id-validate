package com.example.id_validate.controller;

import com.example.id_validate.dto.ValidateRequestDTO;
import com.example.id_validate.dto.ValidateResponseDTO;
import com.example.id_validate.repo.ValidationRecordRepository;
import com.example.id_validate.service.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ids")
@RequiredArgsConstructor
public class ValidationController {

    private final ValidationService service;
    private final ValidationRecordRepository repo;

    @PostMapping("/validate")
    public ResponseEntity<ValidateResponseDTO> validate(@Valid @RequestBody ValidateRequestDTO body) {
        return ResponseEntity.ok(service.validate(body));
    }

    @GetMapping("/records")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/records/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return repo.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
