package telros.task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telros.task.dto.UserContactDto;
import telros.task.dto.UserDto;
import telros.task.exceptions.UserExistsException;
import telros.task.model.User;
import telros.task.model.UserMapper;
import telros.task.repository.UserDbRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserDbRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserDbRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        User userCreate = userRepository.createUser(user);
        log.info("Создан пользователь: {}", userCreate);
        return userMapper.toUserDto(userCreate);
    }

    @Override
    public UserDto updateUser(long userId, UserDto userDto) {
        checkUserExists(userId);
        User user = userRepository.getUser(userId);
        user.setId(userId);
        if (userDto.getLastname() != null) {
            user.setLastname(userDto.getLastname());
        }
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getPatronymic() != null) {
            user.setPatronymic(userDto.getPatronymic());
        }
        if (userDto.getBirthday() != null) {
            user.setBirthday(userDto.getBirthday());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        log.info("Обновлён пользователь: {}", user);
        return userMapper.toUserDto(userRepository.updateUser(user));
    }

    @Override
    public UserDto getUser(long userId) {
        checkUserExists(userId);
        log.info("Получен пользователь с id = {}", userId);
        return userMapper.toUserDto(userRepository.getUser(userId));
    }

    @Override
    public boolean removeUser(long userId) {
        checkUserExists(userId);
        log.info("Удалён пользователь с id = {}", userId);
        return userRepository.removeUser(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        if (userRepository.getAllUsers().size() > 0) {
            for (User user : userRepository.getAllUsers()) {
                userDtoList.add(userMapper.toUserDto(user));
            }
        }
        log.info("Получен список всех пользователей");
        return userDtoList;
    }

    @Override
    public UserContactDto getUserContacts(long userId) {
        checkUserExists(userId);
        log.info("Получены контакты пользователя с id = {}", userId);
        return userMapper.toUserContactDto(userRepository.getUser(userId));
    }

    @Override
    public List<UserContactDto> getAllUsersContacts() {
        List<UserContactDto> userDtoList = new ArrayList<>();
        if (userRepository.getAllUsers().size() > 0) {
            for (User user : userRepository.getAllUsers()) {
                userDtoList.add(userMapper.toUserContactDto(user));
            }
        }
        log.info("Получен список контактов всех пользователей");
        return userDtoList;
    }

    @Override
    public void checkUserExists(long userId) {
        if (userRepository.getUser(userId) == null) {
            log.info("Пользователя с id = {} нет", userId);
            throw new UserExistsException("Пользователя с id = " + userId + " нет");
        }
    }
}

