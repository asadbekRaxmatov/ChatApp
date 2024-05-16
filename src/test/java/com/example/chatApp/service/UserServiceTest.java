package com.example.chatApp.service;

import com.example.chatApp.dto.UserDTO;
import com.example.chatApp.mapper.UserMapper;
import com.example.chatApp.model.User;
import com.example.chatApp.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testAddUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO createdUser = userService.addUser(userDTO);

        assertNotNull(createdUser);
        assertEquals(user.getUsername(), createdUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserDTO foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(userList);

        List<UserDTO> users = userService.getAllUsers();

        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);

        UserDTO updatedUserDTO = userService.updateUser(1L, userDTO);

        assertNotNull(updatedUserDTO);
        assertEquals(userDTO.getUsername(), updatedUserDTO.getUsername());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDto(user);
        verify(userMapper, times(1)).toEntity(userDTO);
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testGetUserByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        UserDTO foundUser = userService.getUserByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testExistsByUsername() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        boolean exists = userService.existsByUsername("testuser");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername("testuser");
    }

    @Test
    public void testGetUsersByCreatedAt() {
        List<User> userList = Arrays.asList(user);
        when(userRepository.findAllByCreatedAt(any(LocalDateTime.class))).thenReturn(userList);

        List<UserDTO> users = userService.getUsersByCreatedAt(user.getCreatedAt());

        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAllByCreatedAt(user.getCreatedAt());
    }

    @Test
    public void testUpdateUsername() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO updatedUser = userService.updateUsername(1L, "newusername");

        assertNotNull(updatedUser);
        assertEquals("newusername", updatedUser.getUsername());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUsersByIds() {
        List<User> userList = Arrays.asList(user);
        when(userRepository.findAllById(anyList())).thenReturn(userList);

        List<UserDTO> users = userService.getUsersByIds(Arrays.asList(1L));

        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAllById(Arrays.asList(1L));
    }

    @Test
    public void testDeleteAllUsers() {
        userService.deleteAllUsers();

        verify(userRepository, times(1)).deleteAll();
    }

    @Test
    public void testSearchUsersByUsername() {
        List<User> userList = Arrays.asList(user);
        when(userRepository.findByUsernameContaining(anyString())).thenReturn(userList);

        List<UserDTO> users = userService.searchUsersByUsername("test");

        assertEquals(1, users.size());
        verify(userRepository, times(1)).findByUsernameContaining("test");
    }

}
