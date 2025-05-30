package cz.uhk.grainweight;

import cz.uhk.grainweight.model.WeightRecord;
import cz.uhk.grainweight.repository.WeightRecordRepository;
import cz.uhk.grainweight.service.WeightRecordServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeightRecordServiceImplTest {

    @Mock
    private WeightRecordRepository weightRecordRepository;

    @InjectMocks
    private WeightRecordServiceImpl weightRecordService;

    @Test
    void testGetAllWeightRecords_whenRecordsExist_returnsList() {
        // Arrange
        WeightRecord r1 = new WeightRecord();
        r1.setId(1L);
        r1.setGrossWeight(100);
        WeightRecord r2 = new WeightRecord();
        r2.setId(2L);
        r2.setGrossWeight(200);
        when(weightRecordRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        // Act
        List<WeightRecord> result = weightRecordService.getAllWeightRecords();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(r1));
        assertTrue(result.contains(r2));
        verify(weightRecordRepository, times(1)).findAll();
    }

    @Test
    void testGetAllWeightRecords_whenNoRecords_returnsEmptyList() {
        // Arrange
        when(weightRecordRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<WeightRecord> result = weightRecordService.getAllWeightRecords();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(weightRecordRepository, times(1)).findAll();
    }

    @Test
    void testGetWeightRecord_whenFound_returnsRecord() {
        // Arrange
        WeightRecord record = new WeightRecord();
        record.setId(1L);
        record.setGrossWeight(150);
        when(weightRecordRepository.findById(1L)).thenReturn(Optional.of(record));

        // Act
        WeightRecord result = weightRecordService.getWeightRecord(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(150.0, result.getGrossWeight());
        verify(weightRecordRepository, times(1)).findById(1L);
    }

    @Test
    void testGetWeightRecord_whenNotFound_returnsNull() {
        // Arrange
        when(weightRecordRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        WeightRecord result = weightRecordService.getWeightRecord(99L);

        // Assert
        assertNull(result);
        verify(weightRecordRepository, times(1)).findById(99L);
    }

    @Test
    void testSaveWeightRecord_savesAndReturnsRecord() {
        // Arrange
        WeightRecord record = new WeightRecord();
        record.setGrossWeight(120);
        WeightRecord saved = new WeightRecord();
        saved.setId(3L);
        saved.setGrossWeight(120);
        when(weightRecordRepository.save(record)).thenReturn(saved);

        // Act
        WeightRecord result = weightRecordService.saveWeightRecord(record);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals(120.0, result.getGrossWeight());
        verify(weightRecordRepository, times(1)).save(record);
    }

    @Test
    void testDeleteWeightRecord_deletesById() {
        // Act
        weightRecordService.deleteWeightRecord(4L);

        // Assert
        verify(weightRecordRepository, times(1)).deleteById(4L);
    }
}
