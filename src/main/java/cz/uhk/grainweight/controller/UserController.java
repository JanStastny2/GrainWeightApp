package cz.uhk.grainweight.controller;

import cz.uhk.grainweight.model.User;
import cz.uhk.grainweight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = { "", "/" })
    public String list(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        User user = userService.getUser(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("User not found: " + id));
        model.addAttribute("user", user);
        return "users_detail";
    }

    @GetMapping("/delete/{id}")
    public String delete( @PathVariable long id) {
       userService.deleteUser(id);
        return "redirect:/users/";
    }

    @GetMapping("/add")
    public String add(Model model) {
        User user = new User();
        user.setRole("USER");
        model.addAttribute("user", user);
        return "users_add";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable long id) {
        User user = userService.getUser(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("User not found: " + id));
        model.addAttribute("user", user);
        return "users_add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users/";
    }

}
