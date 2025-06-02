package cz.uhk.grainweight;

import cz.uhk.grainweight.model.User;
import cz.uhk.grainweight.repository.UserRepository;
import cz.uhk.grainweight.service.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("jan");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("petr");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("jan");
        verify(userRepository).findAll();
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("tester");
        user.setPassword("plainpass");

        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("hashed-password");

        userService.saveUser(user);

        verify(passwordEncoder).encode("plainpass");
        verify(userRepository).save(user);
    }

    @Test
    void testGetUser() {
        long id = 3L;
        User user = new User();
        user.setId(id);
        user.setUsername("john");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getUser(id);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("john");
        verify(userRepository).findById(id);
    }

    @Test
    void testGetUser_NotFound() {
        long id = 100L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        User result = userService.getUser(id);

        assertThat(result).isNull();
        verify(userRepository).findById(id);
    }

    @Test
    void testDeleteUser() {
        long id = 10L;

        userService.deleteUser(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void testFindByUsername() {
        String username = "admin";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        User result = userService.findByUsername(username);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("admin");
        verify(userRepository).findByUsername(username);
    }
}
