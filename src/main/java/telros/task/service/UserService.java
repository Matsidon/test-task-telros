package telros.task.service;

import telros.task.dto.UserContactDto;
import telros.task.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(long userId, UserDto userDto);

    UserDto getUser(long userId);

    boolean removeUser(long userId);

    List<UserDto> getAllUsers();

    void checkUserExists(long userId);

    UserContactDto getUserContacts(long userId);

    List<UserContactDto> getAllUsersContacts();
}
