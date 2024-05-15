package com.example.chatApp.repository;


import com.example.chatApp.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setCreatedAt(LocalDateTime.now());

        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
        assertThat(savedUser.getCreatedAt()).isNotNull();
    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setUsername("testuser");
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        savedUser.setUsername("updatedUser");
        User updatedUser = userRepository.save(savedUser);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUsername()).isEqualTo("updatedUser");
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());

        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertThat(foundUser).isNotPresent();
    }

}
