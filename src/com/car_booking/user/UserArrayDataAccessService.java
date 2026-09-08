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

    @Override
    public Optional<User> findUserById(UUID userId) {
       return users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .or(Optional::empty);
    }

    @Override
    public List<User> getUsers() {
        return users;
    }


}
