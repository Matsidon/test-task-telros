package telros.task;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import telros.task.model.User;
import telros.task.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserRepositoryTest {
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;

    @AfterEach
    public void clearDb() {
        String sql = "delete from telros.public.users";
        jdbcTemplate.update(sql);
    }


    @Test
    public void testFindUserById() {
        User user = new User();
        user.setLastname("lastname");
        user.setName("name");
        user.setPatronymic("patronymic");
        user.setPhoneNumber("+79991110022");
        user.setEmail("login@mail.ru");
        user.setBirthday(LocalDate.of(2000, 5, 12));
        User user2 = userRepository.createUser(user);
        Optional<User> userOptional = Optional.of(userRepository.getUser(user2.getId()));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user1 ->
                        assertThat(user1).hasFieldOrPropertyWithValue("id", user2.getId())
                );
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setLastname("lastname");
        user1.setName("name");
        user1.setPatronymic("patronymic");
        user1.setPhoneNumber("+79991110022");
        user1.setEmail("login@mail.ru");
        user1.setBirthday(LocalDate.of(2000, 5, 12));
        User user2 = new User();
        user2.setLastname("lastname");
        user2.setName("name");
        user2.setPatronymic("patronymic");
        user2.setPhoneNumber("+79991110022");
        user2.setEmail("login1@mail.ru");
        user2.setBirthday(LocalDate.of(2000, 5, 12));
        User user1Create = userRepository.createUser(user1);
        User user2Create = userRepository.createUser(user2);
        List<User> userSet = userRepository.getAllUsers();
        assertThat(userSet)
                .element(0)
                .hasFieldOrPropertyWithValue("id", user1Create.getId());
        assertThat(userSet)
                .element(1)
                .hasFieldOrPropertyWithValue("id", user2Create.getId());
    }

    @Test
    public void testCreateUser() {
        User user1 = new User();
        user1.setLastname("lastname");
        user1.setName("name");
        user1.setPatronymic("patronymic");
        user1.setPhoneNumber("+79991110022");
        user1.setEmail("login@mail.ru");
        user1.setBirthday(LocalDate.of(2000, 5, 12));
        User userCreate = userRepository.createUser(user1);
        user1.setId(userCreate.getId());
        assertThat(userCreate)
                .hasFieldOrPropertyWithValue("id", userCreate.getId())
                .hasFieldOrPropertyWithValue("name", "name");
    }

    @Test
    public void testUpdateUser() {
        User user1 = new User();
        user1.setLastname("lastname");
        user1.setName("name");
        user1.setPatronymic("patronymic");
        user1.setPhoneNumber("+79991110022");
        user1.setEmail("login@mail.ru");
        user1.setBirthday(LocalDate.of(2000, 5, 12));
        User user2 = userRepository.createUser(user1);
        user1.setId(user2.getId());
        User user3 = new User("lastnameUpdate", "nameUpdate",
                "patronymicUpdate", LocalDate.of(2000, 5, 12),
                "loginUpdate@mail.ru", "+79991110022");
        user3.setId(user1.getId());
        User userUpdate = userRepository.updateUser(user3);
        assertThat(userUpdate)
                .hasFieldOrPropertyWithValue("name", "nameUpdate");
    }

    @Test
    public void testDeleteUser() {
        User user1 = new User();
        user1.setLastname("lastname");
        user1.setName("name");
        user1.setPatronymic("patronymic");
        user1.setPhoneNumber("+79991110022");
        user1.setEmail("login@mail.ru");
        user1.setBirthday(LocalDate.of(2000, 5, 12));
        userRepository.createUser(user1);
        boolean deleteUser = userRepository.removeUser(user1.getId());
        assertTrue(deleteUser);
    }
}
