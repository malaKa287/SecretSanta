package SecretSanta.Santa.Controller;


import SecretSanta.Santa.Model.EmailModel;
import SecretSanta.Santa.Model.EmailWrapper;
import SecretSanta.Santa.Model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

//@RestController
@Controller
public class MainController {
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest" ;
    private static final String AJAX_HEADER_NAME = "X-Requested-With";

//    List<String> emails = new ArrayList<>();

//    @Autowired
//    EmailModel emailComponent;

    @PostMapping(params = "addItem", path = {"/santa", "/santa/{id}"})
    public String addOrder(EmailWrapper wrapper, HttpServletRequest request) {
        wrapper.addEmail(new Test());

        System.out.println("enter");

        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            // It is an Ajax request, render only #items fragment of the page.
            return "wrapper::#items";
        } else {
            // It is a standard HTTP request, render whole page.
            return "main";
        }
    }

    @GetMapping("/santa")
    public String userForm(Model model) {

        EmailWrapper wrapper = new EmailWrapper();


        System.out.println("1");
        for (int i = 0; i < 10; i++){
            wrapper.addEmail(new Test());
        }

        System.out.println("2");

//        model.addAttribute("emails", new EmailModel());
        model.addAttribute("emails", wrapper);

        return "main";
    }

    @PostMapping("/santa")
//    public @ResponseBody User
//    public String confirmUser(@ModelAttribute UserList users) {
//    public String confirmEmails(@Valid @ModelAttribute("emails") EmailModel emails, BindingResult bindingResult) {
    public String confirmEmails(@ModelAttribute("emails") EmailWrapper emails, BindingResult bindingResult) {

        List<Test> testList = emails.getEmails();
        System.out.println("testList: " + testList);

        if (bindingResult.hasErrors()){
            System.out.println("error email");
            return "main";
        }

        System.out.println("emails: " + emails.getEmails());

//        String[] allEmails = emails.getEmail().split(",");
//
//        for (int i = 0; i < allEmails.length; i++) {
//            emailComponent.setEmailsMap(String.valueOf(i+1), allEmails[i]);
//        }

        return "redirect:/sendEmail";
    }

}
