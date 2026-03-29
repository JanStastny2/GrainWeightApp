package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    User saveUser(User user);
    User updateUser(long id, User user);
    Optional<User> getUser(long id);
    void deleteUser(long id);
    User findByUsername(String username);
}
