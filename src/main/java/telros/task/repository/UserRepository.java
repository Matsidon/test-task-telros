package telros.task.repository;

import telros.task.model.User;

import java.util.List;

public interface UserRepository {
    User createUser(User user);
    User updateUser(User user);
    User getUser(long userId);
    boolean removeUser(long userId);
    List<User> getAllUsers();
}
