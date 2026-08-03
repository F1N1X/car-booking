package user;

import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> getUserByID(UUID userId) {
        return Optional.ofNullable(userDao.findUserById(userId));
    }

    public User[] getAllUsers() {
        return userDao.getUsers();
    }
}
