package telros.task.model;

import org.springframework.stereotype.Component;
import telros.task.dto.UserContactDto;
import telros.task.dto.UserDto;

@Component
public class UserMapper {
    public User toUser(UserDto userDto) {
        return new User(userDto.getLastname(), userDto.getName(), userDto.getPatronymic(), userDto.getBirthday(),
                userDto.getEmail(), userDto.getPhoneNumber());
    }

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getLastname(), user.getName(), user.getPatronymic(), user.getBirthday(),
                user.getEmail(), user.getPhoneNumber());
    }

    public UserContactDto toUserContactDto(User user) {
        return new UserContactDto(user.getLastname(), user.getName(), user.getPatronymic(), user.getEmail(),
                user.getPhoneNumber());
    }
}
