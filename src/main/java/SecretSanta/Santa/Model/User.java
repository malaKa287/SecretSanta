package SecretSanta.Santa.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    private long id;
    private String email;
    private String name;
}
