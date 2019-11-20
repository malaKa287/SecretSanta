package SecretSanta.Santa.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmailWrapper {
    private List<Test> emails = new ArrayList<>();

    public void addEmail(Test emailModel){
        this.emails.add(emailModel);
    }

    public List<Test> getEmails() {
        return emails;
    }

    public void setEmails(List<Test> emails) {
        this.emails = emails;
    }
}
