package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    User saveUser(User user);
    User updateUser(long id, User user);
    Optional<User> getUser(long id);
    void deleteUser(long id);
    User findByUsername(String username);
}
