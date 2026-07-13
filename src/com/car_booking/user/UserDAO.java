package user;

import exceptions.NoUserFoundException;
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

    public User getUserById(UUID id) {
        for (User user : users)
            if (user.getId().equals(id))
                return user;
        throw new NoUserFoundException("No User with the id " + id + " found");
    }

    public User[] getAllUsers() {
        return users;
    }
}
