package SecretSanta.Santa.Controller;

import SecretSanta.Santa.Service.UserDataService;
import SecretSanta.Santa.Model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/new")
public class UserDataController {

    @Autowired
    UserDataService emailService;


    @GetMapping("/santa")
    public String showPage(Model model){
        model.addAttribute("data", emailService.findAll());

        return "index";
    }

    @PostMapping("/save")
    public String save(UserData emailName){
        emailService.save(emailName);

        return "redirect:/santa";
    }

    @PutMapping("/update")
    public String update(UserData emailName){
        emailService.update(emailName);

        return "redirect:/santa";
    }

    @GetMapping("/delete")
    public String delete(long id){
        emailService.delete(id);

        return "redirect:/santa";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public UserData findOne(long id){
        return emailService.findOne(id);
    }
}
