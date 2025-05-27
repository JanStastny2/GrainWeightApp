package cz.uhk.grainweight.controller;

import cz.uhk.grainweight.model.Driver;
import cz.uhk.grainweight.model.User;
import cz.uhk.grainweight.model.WeightRecord;
import cz.uhk.grainweight.service.WeightRecordService;
import cz.uhk.grainweight.service.DriverService;
import cz.uhk.grainweight.service.FieldService;
import cz.uhk.grainweight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/weightrecords")
public class WeightRecordController {

    private final WeightRecordService weightRecordService;
    private final DriverService driverService;
    private final FieldService fieldService;
    private final UserService userService;

    @Autowired
    public WeightRecordController(WeightRecordService weightRecordService, DriverService driverService, FieldService fieldService, UserService userService) {
        this.weightRecordService = weightRecordService;
        this.driverService = driverService;
        this.fieldService = fieldService;
        this.userService = userService;
    }

    @GetMapping(path = { "", "/" })
    public String listWeightRecords(Model model) {
        model.addAttribute("weightecords", weightRecordService.getAllWeightRecords());
        return "weightrecords_list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("weightrecord", new WeightRecord());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("fields", fieldService.getAllFields());
        model.addAttribute("users", userService.getAllUsers());
        return "weightrecord_form";
    }

    @PostMapping("/save")
    public String saveWeightRecord(@ModelAttribute WeightRecord weightRecord, Authentication authentication) {

        User user = userService.findByUsername(authentication.getName());
        weightRecord.setCreatedBy(user);
        Driver driver = driverService.getDriver(weightRecord.getDriver().getId());
        weightRecord.setTareWeight(driver.getTareWeight());
        weightRecordService.saveWeightRecord(weightRecord);
        return "redirect:/weightrecords";
    }


    @GetMapping("/delete/{id}")
    public String showCreateForm(@PathVariable Long id) {
        weightRecordService.deleteWeightRecord(id);
        return "redirect:/weightrecords";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        WeightRecord weightRecord = weightRecordService.getWeightRecord(id);
        model.addAttribute("weightrecord", weightRecord);
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("fields", fieldService.getAllFields());
        model.addAttribute("users", userService.getAllUsers());
        return "weightrecord_form";
    }

}
