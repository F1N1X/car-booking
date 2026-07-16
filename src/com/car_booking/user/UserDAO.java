package user;

import java.util.Optional;
import java.util.UUID;

public interface UserDAO {

   Optional<User> getUserById(UUID id);
   User[] getAllUsers();
}
