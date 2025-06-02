package cz.uhk.grainweight;

import cz.uhk.grainweight.model.Field;
import cz.uhk.grainweight.repository.FieldRepository;
import cz.uhk.grainweight.service.FieldServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FieldServiceImplTest {

    @Mock
    private FieldRepository fieldRepository;

    @InjectMocks
    private FieldServiceImpl fieldService;

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
    void testGetAllFields() {
        Field field1 = new Field();
        field1.setId(1L);
        field1.setName("Pole 1");

        Field field2 = new Field();
        field2.setId(2L);
        field2.setName("Pole 2");

        List<Field> mockFields = List.of(field1, field2);
        when(fieldRepository.findAll()).thenReturn(mockFields);

        List<Field> fields = fieldService.getAllFields();

        assertThat(fields).hasSize(2);
        assertThat(fields.get(0).getName()).isEqualTo("Pole 1");
        assertThat(fields.get(1).getName()).isEqualTo("Pole 2");
        verify(fieldRepository).findAll();
    }

    @Test
    void testSaveField() {
        Field field = new Field();
        field.setName("Test Field");

        fieldService.saveField(field);

        verify(fieldRepository).save(field);
    }

    @Test
    void testDeleteField() {
        Long id = 10L;

        fieldService.deleteField(id);

        verify(fieldRepository).deleteById(id);
    }

    @Test
    void testGetFieldById() {
        Long id = 5L;
        Field field = new Field();
        field.setId(id);
        field.setName("Test Pole");

        when(fieldRepository.findById(id)).thenReturn(Optional.of(field));

        Field result = fieldService.getField(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Test Pole");

        verify(fieldRepository).findById(id);
    }

    @Test
    void testGetFieldById_notFound() {
        Long id = 100L;

        when(fieldRepository.findById(id)).thenReturn(Optional.empty());

        Field result = fieldService.getField(id);

        assertThat(result).isNull();
        verify(fieldRepository).findById(id);
    }
}
