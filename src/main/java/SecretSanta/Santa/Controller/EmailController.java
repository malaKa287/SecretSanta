package SecretSanta.Santa.Controller;

import SecretSanta.Santa.Model.UserData;
import SecretSanta.Santa.Service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Controller
public class EmailController {

    @Autowired
    UserDataService userDataService;

    @Autowired
    MultipartFileController multipartFileController;

    @Autowired
    JavaMailSender javaMailSender;

    @GetMapping("/sendEmail")
    public String sendEmail() {
        List<UserData> userDataList = userDataService.findAll();

//        for (int i = 0; i < 10; i++) {
            List<UserData> shuffledList = shuffleCollection(userDataList);
            System.out.println(shuffledList);

            Map<String, String> pairedMap = pairMap(userDataList);
            System.out.println("paired map: " + pairedMap);
//        }


//        sendMail(userDataList);

        return "santa";
    }


    public void sendMail(List<UserData> userDataList) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        for (int i = 0; i < userDataList.size(); i++) {
            String emailTo;
            String recipientName;

            if (i == (userDataList.size() - 1)) {
                emailTo = userDataList.get(i).getEmail();
                recipientName = userDataList.get(0).getName();
            } else {
                emailTo = userDataList.get(i).getEmail();
                recipientName = userDataList.get(i + 1).getName();
            }

            try {
                helper = new MimeMessageHelper(message, true);
                helper.setTo(emailTo);
                helper.setText("Ho ho ho, Santa is coming. " + recipientName + " already waiting for your gift.");
                helper.setSubject("Secret Santa");
//                FileSystemResource fileSystemResource = new FileSystemResource(new File("src\\main\\resources\\output\\file.xlsx"));
//                helper.addAttachment("file.xlsx", fileSystemResource, "application/octet-stream;");

                javaMailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("exception sendMail()");
            }
        }
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

    public Map<String, String> pairMap(List<UserData> userDataList){
        Map<String, String> pairedMap = new HashMap<>();
        String senderEmail;
        String recipientEmail;
        for (int i = 0; i < userDataList.size(); i++){
            if (i == (userDataList.size() - 1)) {
                senderEmail = userDataList.get(i).getEmail();
                recipientEmail = userDataList.get(0).getEmail();
            } else {
                senderEmail = userDataList.get(i).getEmail();
                recipientEmail = userDataList.get(i + 1).getEmail();
            }
            pairedMap.put(senderEmail, recipientEmail);
        }
        return pairedMap;
    }

}
