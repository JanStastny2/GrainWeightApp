package cz.uhk.grainweight;

import cz.uhk.grainweight.model.Driver;
import cz.uhk.grainweight.repository.DriverRepository;
import cz.uhk.grainweight.service.DriverServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DriverServiceImplTest {

    @InjectMocks
    private DriverServiceImpl driverService;

    @Mock
    private DriverRepository driverRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDrivers() {
        List<Driver> drivers = List.of(
                new Driver(1L, "Jan Novak", 1200.0, null),
                new Driver(2L, "Petr Svoboda", 1300.0, null)
        );

        when(driverRepository.findAll()).thenReturn(drivers);

        List<Driver> result = driverService.getAllDrivers();

        assertThat(result).hasSize(2);
        verify(driverRepository, times(1)).findAll();
    }

    @Test
    void testSaveDriver() {
        Driver driver = new Driver();
        driver.setDriverName("Test Driver");
        driver.setTareWeight(1400);
        driverService.saveDriver(driver);

        verify(driverRepository).save(driver);
    }

    @Test
    void testDeleteDriver() {
        Long driverId = 5L;

        driverService.deleteDriver(driverId);

        verify(driverRepository).deleteById(driverId);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
}
