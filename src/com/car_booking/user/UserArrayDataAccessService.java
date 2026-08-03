package user;

import java.util.Optional;
import java.util.UUID;

public class UserArrayDataAccessService implements UserDao{
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

    @Override
    public User[] getUsers() {
        return users;
    }

    @Override
    public User findUserById(UUID id) {
        for (var user : users)
            if (user.getId().compareTo(id) == 0)
                return user;
        return null;
    }
}
