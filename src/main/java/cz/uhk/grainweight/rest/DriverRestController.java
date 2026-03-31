package cz.uhk.grainweight.rest;

import cz.uhk.grainweight.model.Driver;
import cz.uhk.grainweight.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverRestController {

    private final DriverService driverService;

    @GetMapping("/getall")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Driver> getDriver(@PathVariable long id) {
        return driverService.getDriver(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public Driver createOrUpdateDriver(@RequestBody Driver driver) {
        driverService.saveDriver(driver);
        return driver;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Driver> deleteDriver(@PathVariable long id) {
        return driverService.getDriver(id)
                .map(driver -> {
                    driverService.deleteDriver(id);
                    return ResponseEntity.ok(driver);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
