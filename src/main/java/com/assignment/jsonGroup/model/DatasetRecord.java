package com.assignment.jsonGroup.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Document(collection = "dataset_records")
public class DatasetRecord {
    @Id
    private String id;
    private String datasetName;
    private Object recordId;
    private Map<String, Object> jsonData;

    public DatasetRecord() {}

    public DatasetRecord(String datasetName, Object recordId, Map<String, Object> jsonData) {
        this.datasetName = datasetName;
        this.recordId = recordId;
        this.jsonData = jsonData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public Object getRecordId() {
        return recordId;
    }

    public void setRecordId(Object recordId) {
        this.recordId = recordId;
    }

    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }
} 