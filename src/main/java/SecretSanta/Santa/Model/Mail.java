package SecretSanta.Santa.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.ui.Model;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Mail {

    private String from;
    private String to;
    private String recipientName;
    private String senderName;
    private String subject;
    private Map model;
}
