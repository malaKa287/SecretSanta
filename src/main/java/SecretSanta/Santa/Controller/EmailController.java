package SecretSanta.Santa.Controller;

import SecretSanta.Santa.Model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmailController {

    @Autowired
    Email email;

    @GetMapping("/sendEmail")
    public String sendEmail(){
        System.out.println("send email");

        System.out.println(email.getEmailsMap());

        return "santa";
    }
}
