package SecretSanta.Santa.Controller;

import SecretSanta.Santa.Service.UserDataService;
import SecretSanta.Santa.Model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
//@RequestMapping("/new")
public class UserDataController {

    @Autowired
    UserDataService userDataService;


    @GetMapping("/santa")
    public String showPage(Model model){
        model.addAttribute("data", userDataService.findAll());

        return "index";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("data") UserData emailName, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println("wrong email");

        } else {
            userDataService.save(emailName);
        }

        return "redirect:/santa";
    }


    @GetMapping("/delete")
    public String delete(long id){
        userDataService.delete(id);

        return "redirect:/santa";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public UserData findOne(long id){
        System.out.println(userDataService.findOne(id));
        return userDataService.findOne(id);
    }
}
