package user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserArrayDataAccessService implements UserDao{
    private static List<User> users;

    static {
        users = new ArrayList<>(
                List.of(new User("Max"),
                        new User("Anna"),
                        new User("Lukas"),
                        new User("Sophie"),
                        new User("Leon"),
                        new User("Marie"),
                        new User("Paul"),
                        new User("Laura")));
    }

    public Optional<User> getUserById(UUID id) {
        for (User user : users)
            if (user.getId().equals(id))
                return Optional.of(user);
        return Optional.empty();
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User findUserById(UUID id) {
        for (var user : users)
            if (user.getId().equals(id))
                return user;
        return null;
    }
}
