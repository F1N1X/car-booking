package user;

import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserArrayDataAccessService userDao;

    public UserService() {
       userDao = new UserArrayDataAccessService();
    }

    public Optional<User> getUserByID(UUID userId) {
        return userDao.getUserById(userId);
    }

    public User[] getAllUsers() {
        return userDao.getAllUsers();
    }
}
