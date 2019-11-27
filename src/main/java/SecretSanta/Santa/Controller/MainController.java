package SecretSanta.Santa.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@RestController
@Controller
public class MainController {

    @PostMapping("/santa")
//    public String confirmEmails(@Valid @ModelAttribute("emails") EmailModel emails, BindingResult bindingResult) {
    public String confirmEmails() {

        return "redirect:/sendEmail";
    }

}
