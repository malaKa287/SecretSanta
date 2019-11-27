package SecretSanta.Santa.Controller;

import SecretSanta.Santa.Model.UserData;
import SecretSanta.Santa.Service.EmailService;
import SecretSanta.Santa.Service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

@Controller
public class EmailController {

    @Autowired
    UserDataService userDataService;

    @Autowired
    MultipartFileController multipartFileController;

    @Autowired
    EmailService emailService;

    @GetMapping("/sendEmail")
    public String sendEmail() {
        List<UserData> userDataList = userDataService.findAll();

        List<UserData> shuffledList = shuffleCollection(userDataList);
//            System.out.println("shuffledList: " + shuffledList);

        emailService.buildEmail(shuffledList);

        return "santa";
    }


    public List<UserData> shuffleCollection(List<UserData> userDataList) {
        Collections.shuffle(userDataList);

        Map<String, String> excelMap = multipartFileController.getExcelMap();

        String senderEmail;
        String recipientEmail;
        for (int i = 0; i < userDataList.size(); i++) {
            if (i == (userDataList.size() - 1)) {
                senderEmail = userDataList.get(i).getEmail();
                recipientEmail = userDataList.get(0).getEmail();
            } else {
                senderEmail = userDataList.get(i).getEmail();
                recipientEmail = userDataList.get(i + 1).getEmail();
            }
            for (Map.Entry<String, String> entry : excelMap.entrySet()) {
                String emailFromBanned = entry.getKey();
                String emailToBanned = entry.getValue();

                if (senderEmail.equals(emailFromBanned) && recipientEmail.equals(emailToBanned)) {
                    shuffleCollection(userDataList);
                }
            }
        }

        return userDataList;
    }


//    public Map<String, String> pairMap(List<UserData> userDataList){
//        Map<String, String> pairedMap = new HashMap<>();
//        String senderEmail;
//        String recipientEmail;
//        for (int i = 0; i < userDataList.size(); i++){
//            if (i == (userDataList.size() - 1)) {
//                senderEmail = userDataList.get(i).getEmail();
//                recipientEmail = userDataList.get(0).getEmail();
//            } else {
//                senderEmail = userDataList.get(i).getEmail();
//                recipientEmail = userDataList.get(i + 1).getEmail();
//            }
//            pairedMap.put(senderEmail, recipientEmail);
//        }
//        return pairedMap;
//    }


}
