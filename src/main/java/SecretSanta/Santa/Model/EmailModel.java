package SecretSanta.Santa.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmailModel {

    @NotNull
    @Email(message = "wrong email")
    private String email;
    private List<String> emails;
    private Map<String, String> emailsMap = new HashMap<>();

    public void setEmailsMap(String key, String value) {
        emailsMap.put(key, value);
    }
}
