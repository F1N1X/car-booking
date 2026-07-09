package user;

import java.util.UUID;

public class UserService {

    private final UserDAO userDao;

    public UserService() {
       userDao = new UserDAO();
    }

    public User getUser(UUID userId) {
        return userDao.getUserById(userId);
    }

    public String getAllUsers() {
        return userDao.getAllUsers();
    }
}
