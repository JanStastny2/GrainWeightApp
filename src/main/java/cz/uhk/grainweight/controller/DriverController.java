package cz.uhk.grainweight.controller;

import cz.uhk.grainweight.model.Driver;
import cz.uhk.grainweight.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @GetMapping(path = { "", "/" })
    public String list(Model model) {
        model.addAttribute("drivers", driverService.getAllDrivers());
        return "drivers_list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return "redirect:/drivers";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("driver", new Driver());
        return "drivers_add";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable long id) {
        Driver driver = driverService.getDriver(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("Driver not found: " + id));
        model.addAttribute("driver", driver);
        return "drivers_add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Driver driver) {
        driverService.saveDriver(driver);
        return "redirect:/drivers/";
    }

}
