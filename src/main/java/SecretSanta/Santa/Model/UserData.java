package SecretSanta.Santa.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserData {
    private long id;
    private String name;
    private String email;

    public UserData(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
