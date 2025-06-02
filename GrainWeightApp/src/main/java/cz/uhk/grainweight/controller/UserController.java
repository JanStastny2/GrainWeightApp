package cz.uhk.grainweight.controller;

import cz.uhk.grainweight.model.User;
import cz.uhk.grainweight.model.WeightRecord;
import cz.uhk.grainweight.service.UserService;
import cz.uhk.grainweight.service.WeightRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeightRecordService weightRecordService;

    @Autowired
    public UserController(UserService userService, WeightRecordService weightRecordService) {
        this.userService = userService;
        this.weightRecordService = weightRecordService;
    }

    @GetMapping(path = { "", "/" })
    public String list(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users_list";
    }

    @GetMapping("/users/2")
    public String showUserDetail() {
        return "redirect:/drivers/";
    }



    @GetMapping("/delete/{id}")
    public String delete( @PathVariable long id) {
       userService.deleteUser(id);
        return "redirect:/users/";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        return "users_add";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("user", userService.getUser(id));
        return "users_add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users/";
    }

}
