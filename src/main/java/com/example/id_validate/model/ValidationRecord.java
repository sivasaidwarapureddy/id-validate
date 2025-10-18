package com.example.id_validate.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("validation_records")
public class ValidationRecord {
    @Id
    private String id;
    @Indexed
    private String type;
    @Indexed
    private String input;
    private String normalized;
    private boolean valid;
    private String message;
    private Instant createdAt;
}
