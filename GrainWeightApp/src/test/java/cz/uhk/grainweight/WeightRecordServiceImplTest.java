package cz.uhk.grainweight;

import cz.uhk.grainweight.model.WeightRecord;
import cz.uhk.grainweight.repository.WeightRecordRepository;
import cz.uhk.grainweight.service.WeightRecordServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WeightRecordServiceImplTest {

    @Mock
    private WeightRecordRepository weightRecordRepository;

    @InjectMocks
    private WeightRecordServiceImpl weightRecordService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetAllWeightRecords() {
        WeightRecord wr1 = new WeightRecord();
        wr1.setId(1L);
        wr1.setDate(LocalDateTime.now());

        WeightRecord wr2 = new WeightRecord();
        wr2.setId(2L);
        wr2.setDate(LocalDateTime.now().minusDays(1));

        when(weightRecordRepository.findAll()).thenReturn(List.of(wr1, wr2));

        List<WeightRecord> result = weightRecordService.getAllWeightRecords();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(weightRecordRepository).findAll();
    }

    @Test
    void testGetWeightRecord() {
        Long id = 10L;
        WeightRecord wr = new WeightRecord();
        wr.setId(id);
        wr.setGrossWeight(5000);

        when(weightRecordRepository.findById(id)).thenReturn(Optional.of(wr));

        WeightRecord result = weightRecordService.getWeightRecord(id);

        assertThat(result).isNotNull();
        assertThat(result.getGrossWeight()).isEqualTo(5000.0);
        verify(weightRecordRepository).findById(id);
    }

    @Test
    void testGetWeightRecord_NotFound() {
        Long id = 99L;
        when(weightRecordRepository.findById(id)).thenReturn(Optional.empty());

        WeightRecord result = weightRecordService.getWeightRecord(id);

        assertThat(result).isNull();
        verify(weightRecordRepository).findById(id);
    }

    @Test
    void testSaveWeightRecord() {
        WeightRecord wr = new WeightRecord();
        wr.setGrossWeight(5500);

        when(weightRecordRepository.save(wr)).thenReturn(wr);

        WeightRecord saved = weightRecordService.saveWeightRecord(wr);

        assertThat(saved.getGrossWeight()).isEqualTo(5500.0);
        verify(weightRecordRepository).save(wr);
    }

    @Test
    void testDeleteWeightRecord() {
        Long id = 7L;

        weightRecordService.deleteWeightRecord(id);

        verify(weightRecordRepository).deleteById(id);
    }
}
