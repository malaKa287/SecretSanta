package SecretSanta.Santa.Controller;

import SecretSanta.Santa.Model.NewEmailService;
import SecretSanta.Santa.Model.UserEmailName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/new")
public class NewEmailController {

    @Autowired
    NewEmailService emailService;

    @GetMapping("/")
    public String showPage(Model model){
        model.addAttribute("data", emailService.findAll());

        return "index";
    }

    @PostMapping("/save")
    public String save(UserEmailName emailName){
        emailService.save(emailName);

        return "redirect:/";
    }

    @PutMapping("/update")
    public String update(UserEmailName emailName){
        emailService.update(emailName);

        return "redirect:/";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public UserEmailName findOne(long id){
        return emailService.findOne(id);
    }
}
