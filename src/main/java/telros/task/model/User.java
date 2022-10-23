package telros.task.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class User {
    private long id;
    private String lastname;
    private String name;
    private String patronymic;
    private LocalDate birthday;
    private String email;
    private String phoneNumber;

    public User(String lastname, String name, String patronymic, LocalDate birthday, String email, String phoneNumber) {
        this.lastname = lastname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
