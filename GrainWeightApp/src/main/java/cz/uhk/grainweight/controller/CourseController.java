package cz.uhk.grainweight.controller;

import cz.uhk.grainweight.model.Course;
import cz.uhk.grainweight.service.CourseService;
import cz.uhk.grainweight.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final DriverService driverService;

    @Autowired
    public CourseController(CourseService courseService, DriverService driverService) {
        this.courseService = courseService;
        this.driverService = driverService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        model.addAttribute("course", courseService.getCourse(id));
        return "courses_detail";
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable long id) {
        model.addAttribute("course", courseService.getCourse(id));
        return "courses_delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteConfirm(Model model, @PathVariable long id) {
        courseService.deleteCourse(id);
        return "redirect:/courses/";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("lecturers", driverService.getAllDrivers());
        return "courses_add";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("course", courseService.getCourse(id));
        model.addAttribute("lecturers", driverService.getAllDrivers());
        return "courses_add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Course course) {
        courseService.saveCourse(course);
        return "redirect:/courses/";
    }

}
