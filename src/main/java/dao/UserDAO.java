package dao;

import exceptions.NoCapacityException;
import exceptions.NoUserFoundException;
import models.user.User;

import java.util.Arrays;
import java.util.UUID;

public class UserDAO {
    private static final User[] users;
    private static int capacity;

    static {
        users = new User[10];
        capacity = users.length;
    }

    public void addUser(String name) {

        if (capacity == 0) throw new NoCapacityException("The max capacity is reached");

        User user = new User(name);

        users[users.length % capacity] = user;
        capacity--;
    }

    public String getUsers() {
        return Arrays.toString(users);
    }

    public User getUserById(UUID id) {
        for (User user : users)
            if (user.getId().equals(id))
                return user;

        throw new NoUserFoundException("No User with the id " + id + " found");
    }


}
