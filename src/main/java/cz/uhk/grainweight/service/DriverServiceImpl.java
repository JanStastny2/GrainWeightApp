package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.Driver;
import cz.uhk.grainweight.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> getAllDrivers() {
        log.info("Fetching all drivers");
        return driverRepository.findAll();
    }

    @Override
    public void saveDriver(Driver driver) {
        log.info("Saving driver: {}", driver.getDriverName());
        driverRepository.save(driver);
    }

    @Override
    public Optional<Driver> getDriver(long id) {
        return driverRepository.findById(id);
    }

    @Override
    public void deleteDriver(long id) {
        driverRepository.deleteById(id);
    }
}
