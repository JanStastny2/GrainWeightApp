package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    List<Driver> getAllDrivers();
    void saveDriver(Driver driver);
    Optional<Driver> getDriver(long id);
    void deleteDriver(long id);
}
