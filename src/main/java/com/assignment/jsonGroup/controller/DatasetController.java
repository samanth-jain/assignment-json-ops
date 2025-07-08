package com.assignment.jsonGroup.controller;

import com.assignment.jsonGroup.model.DatasetRecord;
import com.assignment.jsonGroup.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dataset/{datasetName}")
public class DatasetController {
    @Autowired
    private DatasetService datasetService;

    @PostMapping("/record")
    public ResponseEntity<?> insertRecord(
            @PathVariable String datasetName,
            @RequestBody Map<String, Object> record
    ) {
        DatasetRecord saved = datasetService.insertRecord(datasetName, record);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Record added successfully");
        response.put("dataset", datasetName);
        response.put("recordId", saved.getRecordId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/query")
    public ResponseEntity<?> queryRecords(
            @PathVariable String datasetName,
            @RequestParam(required = false) String groupBy,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order
    ) {
        List<DatasetRecord> records = datasetService.getRecordsByDataset(datasetName);
        if (groupBy != null && !groupBy.isEmpty()) {
            Map<String, List<Map<String, Object>>> grouped = datasetService.groupByField(records, groupBy);
            Map<String, Object> response = new HashMap<>();
            response.put("groupedRecords", grouped);
            return ResponseEntity.ok(response);
        } else if (sortBy != null && !sortBy.isEmpty()) {
            List<Map<String, Object>> sorted = datasetService.sortByField(records, sortBy, order);
            Map<String, Object> response = new HashMap<>();
            response.put("sortedRecords", sorted);
            return ResponseEntity.ok(response);
        } else {
            // Return all records as is
            List<Map<String, Object>> all = new ArrayList<>();
            for (DatasetRecord r : records) {
                all.add(r.getJsonData());
            }
            Map<String, Object> response = new HashMap<>();
            response.put("records", all);
            return ResponseEntity.ok(response);
        }
    }
} 