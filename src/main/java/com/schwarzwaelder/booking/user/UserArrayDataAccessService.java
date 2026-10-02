package com.schwarzwaelder.booking.user;

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
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",", 2);

                    UUID id = UUID.fromString(parts[0].trim());
                    String name = parts[1].trim();

                    users.add(new User(id, name));
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
