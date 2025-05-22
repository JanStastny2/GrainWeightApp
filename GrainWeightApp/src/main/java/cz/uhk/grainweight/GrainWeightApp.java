package cz.uhk.grainweight;

import cz.uhk.grainweight.model.User;
import cz.uhk.grainweight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GrainWeightApp {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public GrainWeightApp(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            addUser("User", "user", "heslo", "USER");
            addUser("Admin", "admin", "heslo", "ADMIN");
        };
    }

    private void addUser(String name, String username, String password, String role){
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        userService.saveUser(user);
    }

    public static void main(String[] args) {
        SpringApplication.run(GrainWeightApp.class, args);
    }

}
