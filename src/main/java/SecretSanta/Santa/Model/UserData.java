package SecretSanta.Santa.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserData {
    private long id;
    private String name;
    @Email(message = "wrong email")
    private String email;

    public UserData(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
