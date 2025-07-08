package com.assignment.jsonGroup.controller;

import com.assignment.jsonGroup.model.DatasetRecord;
import com.assignment.jsonGroup.service.DatasetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DatasetControllerTests {
    private MockMvc mockMvc;

    @Mock
    private DatasetService datasetService;

    @InjectMocks
    private DatasetController datasetController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(datasetController).build();
    }

    @Test
    void testInsertRecord() throws Exception {
        Map<String, Object> record = new HashMap<>();
        record.put("id", 1);
        record.put("name", "John");
        DatasetRecord saved = new DatasetRecord("employee_dataset", 1, record);
        when(datasetService.insertRecord(any(), any())).thenReturn(saved);
        mockMvc.perform(post("/api/dataset/employee_dataset/record")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Record added successfully"))
                .andExpect(jsonPath("$.dataset").value("employee_dataset"))
                .andExpect(jsonPath("$.recordId").value(1));
    }

    @Test
    void testQueryGroupBy() throws Exception {
        Map<String, Object> rec1 = new HashMap<>();
        rec1.put("department", "Engineering");
        rec1.put("id", 1);
        List<DatasetRecord> records = Collections.singletonList(new DatasetRecord("employee_dataset", 1, rec1));
        Map<String, List<Map<String, Object>>> grouped = new HashMap<>();
        grouped.put("Engineering", Collections.singletonList(rec1));
        when(datasetService.getRecordsByDataset(any())).thenReturn(records);
        when(datasetService.groupByField(records, "department")).thenReturn(grouped);
        mockMvc.perform(get("/api/dataset/employee_dataset/query?groupBy=department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupedRecords.Engineering[0].id").value(1));
    }

    @Test
    void testQuerySortBy() throws Exception {
        Map<String, Object> rec1 = new HashMap<>();
        rec1.put("age", 30);
        rec1.put("id", 1);
        List<DatasetRecord> records = Collections.singletonList(new DatasetRecord("employee_dataset", 1, rec1));
        List<Map<String, Object>> sorted = Collections.singletonList(rec1);
        when(datasetService.getRecordsByDataset(any())).thenReturn(records);
        when(datasetService.sortByField(records, "age", "asc")).thenReturn(sorted);
        mockMvc.perform(get("/api/dataset/employee_dataset/query?sortBy=age&order=asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sortedRecords[0].age").value(30));
    }
} 