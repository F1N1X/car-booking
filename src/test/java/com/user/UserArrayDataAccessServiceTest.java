package com.user;

import com.schwarzwaelder.booking.user.User;
import com.schwarzwaelder.booking.user.UserArrayDataAccessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserArrayDataAccessServiceTest {

    private UserArrayDataAccessService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserArrayDataAccessService();
    }

    @Test
    void findUserById() {
        // given
        User expected = underTest.getUsers().get(0);
        // when
        Optional<User> actual = underTest.findUserById(expected.getId());
        // then
        assert(actual.isPresent());
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    void getUsers() {
        // when
        List<User> actual = underTest.getUsers();

        // then
        assertThat(actual)
                .isNotNull()
                .isNotEmpty();
    }
}