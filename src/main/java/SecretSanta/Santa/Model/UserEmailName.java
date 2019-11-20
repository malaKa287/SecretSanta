package SecretSanta.Santa.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserEmailName {
    private long id;
    private String name;
    private String email;

    public UserEmailName(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
