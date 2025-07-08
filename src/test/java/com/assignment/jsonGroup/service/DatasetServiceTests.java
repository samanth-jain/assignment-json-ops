package com.assignment.jsonGroup.service;

import com.assignment.jsonGroup.model.DatasetRecord;
import com.assignment.jsonGroup.repository.DatasetRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatasetServiceTests {
    @Mock
    private DatasetRecordRepository repository;

    @InjectMocks
    private DatasetService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertRecord() {
        Map<String, Object> record = new HashMap<>();
        record.put("id", 1);
        record.put("name", "John");
        DatasetRecord saved = new DatasetRecord("test", 1, record);
        when(repository.save(any())).thenReturn(saved);
        DatasetRecord result = service.insertRecord("test", record);
        assertEquals("test", result.getDatasetName());
        assertEquals(1, result.getRecordId());
        assertEquals("John", result.getJsonData().get("name"));
    }

    @Test
    void testGroupByField() {
        Map<String, Object> rec1 = new HashMap<>();
        rec1.put("department", "Engineering");
        rec1.put("id", 1);
        Map<String, Object> rec2 = new HashMap<>();
        rec2.put("department", "HR");
        rec2.put("id", 2);
        Map<String, Object> rec3 = new HashMap<>();
        rec3.put("department", "Engineering");
        rec3.put("id", 3);
        List<DatasetRecord> records = Arrays.asList(
                new DatasetRecord("ds", 1, rec1),
                new DatasetRecord("ds", 2, rec2),
                new DatasetRecord("ds", 3, rec3)
        );
        Map<String, List<Map<String, Object>>> grouped = service.groupByField(records, "department");
        assertEquals(2, grouped.size());
        assertTrue(grouped.containsKey("Engineering"));
        assertEquals(2, grouped.get("Engineering").size());
    }

    @Test
    void testSortByFieldAsc() {
        Map<String, Object> rec1 = new HashMap<>();
        rec1.put("age", 30);
        rec1.put("id", 1);
        Map<String, Object> rec2 = new HashMap<>();
        rec2.put("age", 25);
        rec2.put("id", 2);
        List<DatasetRecord> records = Arrays.asList(
                new DatasetRecord("ds", 1, rec1),
                new DatasetRecord("ds", 2, rec2)
        );
        List<Map<String, Object>> sorted = service.sortByField(records, "age", "asc");
        assertEquals(25, sorted.get(0).get("age"));
        assertEquals(30, sorted.get(1).get("age"));
    }

    @Test
    void testSortByFieldDesc() {
        Map<String, Object> rec1 = new HashMap<>();
        rec1.put("age", 30);
        rec1.put("id", 1);
        Map<String, Object> rec2 = new HashMap<>();
        rec2.put("age", 25);
        rec2.put("id", 2);
        List<DatasetRecord> records = Arrays.asList(
                new DatasetRecord("ds", 1, rec1),
                new DatasetRecord("ds", 2, rec2)
        );
        List<Map<String, Object>> sorted = service.sortByField(records, "age", "desc");
        assertEquals(30, sorted.get(0).get("age"));
        assertEquals(25, sorted.get(1).get("age"));
    }
} 