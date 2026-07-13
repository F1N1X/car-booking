package user;

import java.util.Objects;
import java.util.UUID;

public class UserService {

    private final UserDAO userDao;

    public UserService() {
       userDao = new UserDAO();
    }

    public User getUser(UUID userId) {
        return userDao.getUserById(userId);
    }

    public User[] getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean checkUserExist(UUID userUuid) {
        return !Objects.isNull(getUser(userUuid));
    }
}
