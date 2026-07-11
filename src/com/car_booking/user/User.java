package user;

import java.util.UUID;

public class User {

    private final UUID id;
    private final String name;

    public User(String name) {
        this.name = name;
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
