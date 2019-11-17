package SecretSanta.Santa.Controller;


import SecretSanta.Santa.Model.Email;
import SecretSanta.Santa.Model.User;
import SecretSanta.Santa.Model.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@RestController
@Controller
public class MainController {

//    List<String> emails = new ArrayList<>();

    @Autowired
    Email emailComponent;

    @GetMapping("/santa")
    public String userForm(Model model) {
//        model.addAttribute("users", new UserList());
        model.addAttribute("emails", new Email());

        return "main";
    }

    @PostMapping("/santa")
//    public @ResponseBody User
//    public String confirmUser(@ModelAttribute UserList users) {
    public String confirmUser(@ModelAttribute("emails") Email email) {
//    public String confirmUser(@ModelAttribute List<User> user) {
//        this.user.setEmail(user.getEmail());

        String[] allEmails = email.getEmail().split(",");
//        emailComponent.setEmails(Arrays.asList(allEmails));

        for (int i = 0; i < allEmails.length; i++) {
            emailComponent.setEmailsMap(String.valueOf(i+1), allEmails[i]);
        }

        return "redirect:/sendEmail";
    }
}
