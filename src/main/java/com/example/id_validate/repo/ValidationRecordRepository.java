package com.example.id_validate.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.id_validate.model.ValidationRecord;

public interface ValidationRecordRepository extends MongoRepository<ValidationRecord, String> { }
