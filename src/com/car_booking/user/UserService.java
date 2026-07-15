package user;

import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserDAO userDao;

    public UserService() {
       userDao = new UserDAO();
    }

    public Optional<User> getUserByID(UUID userId) {
        return userDao.getUserById(userId);
    }

    public User[] getAllUsers() {
        return userDao.getAllUsers();
    }
}
