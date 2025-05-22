package cz.uhk.grainweight.rest;

import cz.uhk.grainweight.model.User;
import cz.uhk.grainweight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getall")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/delete/{id}")
    public User deleteUser(@PathVariable long id) {
        User user = userService.getUser(id);
        userService.deleteUser(id);
        return user;
    }

    @PostMapping("/new")
    public User newUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

}
