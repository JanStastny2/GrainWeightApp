package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.User;
import cz.uhk.grainweight.repository.UserRepository;
import cz.uhk.grainweight.security.MyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
    }

    @Test
    void saveUser_ShouldSave_WhenUsernameIsUnique() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void saveUser_ShouldThrowException_WhenUsernameExists() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    void updateUser_WhenPasswordProvided_ShouldEncodeAndSave() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("olduser");
        existingUser.setPassword("oldPassword");
        existingUser.setName("Old Name");
        existingUser.setRole("USER");

        User update = new User();
        update.setUsername("newuser");
        update.setPassword("newPassword");
        update.setName("New Name");
        update.setRole("ADMIN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.updateUser(1L, update);

        assertEquals("newuser", result.getUsername());
        assertEquals("encodedNewPassword", result.getPassword());
        assertEquals("New Name", result.getName());
        assertEquals("ADMIN", result.getRole());
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_WhenPasswordBlank_ShouldKeepExistingPassword() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("olduser");
        existingUser.setPassword("oldPassword");
        existingUser.setName("Old Name");
        existingUser.setRole("USER");

        User update = new User();
        update.setUsername("newuser");
        update.setPassword("   ");
        update.setName("New Name");
        update.setRole("ADMIN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.updateUser(1L, update);

        assertEquals("oldPassword", result.getPassword());
        assertEquals("newuser", result.getUsername());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void loadUserByUsername_WhenUserExists_ShouldReturnMyUserDetails() {
        user.setRole("USER");
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        UserDetails result = userService.loadUserByUsername("testuser");

        assertInstanceOf(MyUserDetails.class, result);
        assertEquals("testuser", result.getUsername());
        assertEquals("password", result.getPassword());
    }

    @Test
    void loadUserByUsername_WhenUserMissing_ShouldThrowUsernameNotFoundException() {
        when(userRepository.findByUsername("missing")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("missing"));
    }
}
