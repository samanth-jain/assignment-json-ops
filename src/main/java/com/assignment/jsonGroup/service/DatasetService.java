package com.assignment.jsonGroup.service;

import com.assignment.jsonGroup.model.DatasetRecord;
import com.assignment.jsonGroup.repository.DatasetRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DatasetService {
    @Autowired
    private DatasetRecordRepository repository;

    public DatasetRecord insertRecord(String datasetName, Map<String, Object> record) {
        Object recordId = record.get("id");
        DatasetRecord datasetRecord = new DatasetRecord(datasetName, recordId, record);
        return repository.save(datasetRecord);
    }

    public List<DatasetRecord> getRecordsByDataset(String datasetName) {
        return repository.findByDatasetName(datasetName);
    }

    public Map<String, List<Map<String, Object>>> groupByField(List<DatasetRecord> records, String groupBy) {
        return records.stream()
                .filter(r -> r.getJsonData().containsKey(groupBy))
                .collect(Collectors.groupingBy(
                        r -> String.valueOf(r.getJsonData().get(groupBy)),
                        Collectors.mapping(DatasetRecord::getJsonData, Collectors.toList())
                ));
    }

    public List<Map<String, Object>> sortByField(List<DatasetRecord> records, String sortBy, String order) {
        Comparator<DatasetRecord> comparator = Comparator.comparing(
                r -> (Comparable) r.getJsonData().get(sortBy), Comparator.nullsLast(Comparator.naturalOrder())
        );
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        return records.stream()
                .sorted(comparator)
                .map(DatasetRecord::getJsonData)
                .collect(Collectors.toList());
    }
} 