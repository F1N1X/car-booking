package user;

import java.util.Optional;
import java.util.UUID;

public class UserDAO {
    private static final User[] users;

    static {
        users = new User[]
        {
                new User("Max"),
                new User("Anna"),
                new User("Lukas"),
                new User("Sophie"),
                new User("Leon"),
                new User("Marie"),
                new User("Paul"),
                new User("Laura"),
        };
    }

    public Optional<User> getUserById(UUID id) {
        for (User user : users)
            if (user.getId().compareTo(id) == 0)
                return Optional.of(user);
        return Optional.empty();
    }

    public User[] getAllUsers() {
        return users;
    }
}
