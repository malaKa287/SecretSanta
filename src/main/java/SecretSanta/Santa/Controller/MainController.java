package SecretSanta.Santa.Controller;


import SecretSanta.Santa.Model.User;
import SecretSanta.Santa.Model.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
public class MainController {

    @Autowired
    User user;

    @Autowired
    UserList userList;

    @GetMapping("/santa")
    public String userForm(Model model) {
//        model.addAttribute("users", new UserList());
        model.addAttribute("user", new User());

        return "main";
    }

    @PostMapping("/santa")
//    public @ResponseBody User
//    public String confirmUser(@ModelAttribute UserList users) {
//    public String confirmUser(@ModelAttribute User user) {
    public String confirmUser(@ModelAttribute List<User> user) {
//        this.user.setEmail(user.getEmail());

        System.out.println(user);

        return "main";
    }
}
