package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.Driver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DriverService {
    List<Driver> getAllDrivers();
    void saveDriver(Driver driver);
    Optional<Driver> getDriver(long id);
    void deleteDriver(long id);
}
