package com.user;

import java.io.InputStream;
import java.util.*;

public class UserArrayDataAccessService implements UserDao{
    private static List<User> users;

    static {
        users = new ArrayList<>();

        InputStream in = UserArrayDataAccessService.class
                .getClassLoader()
                .getResourceAsStream("users.csv");

        if (in == null) {
            throw new IllegalStateException("users.csv not found");
        }

        try (Scanner scanner = new Scanner(in)) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();

                if (!name.isEmpty()) {
                    users.add(new User(name));
                }
            }
        }
    }

    @Override
    public Optional<User> findUserById(UUID userId) {
        return users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst();
    }

    @Override
    public List<User> getUsers() {
        return users;
    }
}
