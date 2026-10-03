package com.user;

import com.schwarzwaelder.booking.user.User;
import com.schwarzwaelder.booking.user.UserDao;
import com.schwarzwaelder.booking.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userDao);
    }

    @Test
    void getUserByID() {
        // given
        UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        User expected = new User(
                id,
                "Test5");
        // when
        when(userDao.findUserById(id)).thenReturn(Optional.of(expected));
        Optional<User> actual = underTest.getUserByID(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        // then
        verify(userDao).findUserById(expected.getId());
        assertTrue(actual.isPresent());
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    void getAllUsers() {
        // given
        List<User> expected = new ArrayList(
                List.of(
                        new User(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),"Test1"),
                        new User(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),"Test1"),
                        new User(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),"Test1")));
        // when
        when(userDao.getUsers()).thenReturn(expected);
        List<User> actual = underTest.getAllUsers();
        // then
        verify(userDao).getUsers();
        assertThat(actual).isEqualTo(expected);
    }
}