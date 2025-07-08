package com.assignment.jsonGroup.repository;

import com.assignment.jsonGroup.model.DatasetRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface DatasetRecordRepository extends MongoRepository<DatasetRecord, String> {
    List<DatasetRecord> findByDatasetName(String datasetName);
} 