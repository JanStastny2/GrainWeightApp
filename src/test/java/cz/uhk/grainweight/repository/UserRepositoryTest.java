package cz.uhk.grainweight.repository;

import cz.uhk.grainweight.model.User;
import cz.uhk.grainweight.service.DriverService;
import cz.uhk.grainweight.service.FieldService;
import cz.uhk.grainweight.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @MockitoBean
    private UserService userService;
    @MockitoBean
    private PasswordEncoder passwordEncoder;
    @MockitoBean
    private DriverService driverService;
    @MockitoBean
    private FieldService fieldService;

    @Test
    void save_ShouldPersistUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole("USER");
        user.setName("Test Name");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("testuser", savedUser.getUsername());
    }

    @Test
    void findByUsername_ShouldReturnPersistedUser() {
        User user = new User();
        user.setUsername("lookup-user");
        user.setPassword("password");
        user.setRole("USER");

        userRepository.save(user);

        User foundUser = userRepository.findByUsername("lookup-user");

        assertNotNull(foundUser);
        assertEquals("lookup-user", foundUser.getUsername());
    }

    @Test
    void existsByUsername_ShouldReturnTrueForPersistedUser() {
        User user = new User();
        user.setUsername("existing-user");
        user.setPassword("password");
        user.setRole("USER");

        userRepository.save(user);

        assertTrue(userRepository.existsByUsername("existing-user"));
        assertFalse(userRepository.existsByUsername("missing-user"));
    }
}
