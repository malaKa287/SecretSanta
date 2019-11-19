package SecretSanta.Santa.Controller;


import SecretSanta.Santa.Model.EmailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

//@RestController
@Controller
public class MainController {

//    List<String> emails = new ArrayList<>();

    @Autowired
    EmailModel emailComponent;

    @GetMapping("/santa")
    public String userForm(Model model) {
//        model.addAttribute("users", new UserList());
        model.addAttribute("emails", new EmailModel());

        return "main";
    }

    @PostMapping("/santa")
//    public @ResponseBody User
//    public String confirmUser(@ModelAttribute UserList users) {
    public String confirmEmails(@Valid @ModelAttribute("emails") EmailModel emails, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            System.out.println("error email");
            return "main";
        }

        String[] allEmails = emails.getEmail().split(",");

        for (int i = 0; i < allEmails.length; i++) {
            emailComponent.setEmailsMap(String.valueOf(i+1), allEmails[i]);
        }

        return "redirect:/sendEmail";
    }

}
