package telros.task.controller;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import telros.task.Create;
import telros.task.Update;
import telros.task.dto.UserContactDto;
import telros.task.dto.UserDto;
import telros.task.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Data
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto createUser(@Valid @Validated(Create.class) @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@Valid @Validated(Update.class) @PathVariable long userId, @RequestBody UserDto userDto) {
        return userService.updateUser(userId, userDto);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable long userId) {
        return userService.getUser(userId);
    }

    @DeleteMapping("/{userId}")
    public boolean removeUser(@PathVariable long userId) {
        return userService.removeUser(userId);
    }

    @GetMapping()
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/contacts/{userId}")
    public UserContactDto getUserContacts(@PathVariable long userId) {
        return userService.getUserContacts(userId);
    }

    @GetMapping("/contacts")
    public List<UserContactDto> getAllUsersContacts() {
        return userService.getAllUsersContacts();
    }
}
