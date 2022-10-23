package telros.task.dto;

import lombok.*;
import telros.task.Create;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class UserDto {
    private long id;
    @NotNull(groups = Create.class)
    @NotBlank
    private String lastname;
    @NotNull(groups = Create.class)
    @NotBlank
    private String name;
    @NotNull(groups = Create.class)
    @NotBlank
    private String patronymic;
    @NotNull(groups = Create.class)
    @Past(message = "Дата рождения должна быть раньше текущего момента")
    private LocalDate birthday;
    @NotNull(groups = Create.class)
    @NotBlank
    @Email(message = "Email должен быть корректным адресом электронной почты")
    private String email;
    @NotNull(groups = Create.class)
    @NotBlank
    @Pattern(regexp = "\\+7[0-9]{10}", message = "Телефонный номер должен начинаться с +7, затем - 10 цифр")
    private String phoneNumber;
}
