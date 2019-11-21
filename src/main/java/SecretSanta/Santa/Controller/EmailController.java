package SecretSanta.Santa.Controller;

import SecretSanta.Santa.Model.UserData;
import SecretSanta.Santa.Model.UserDataService;
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
    JavaMailSender javaMailSender;

    @GetMapping("/sendEmail")
    public String sendEmail() {
        List<UserData> userDataList = userDataService.findAll();

//        for (int i = 0; i < 10; i++) {
        List<UserData> shuffledList = shuffleCollection(userDataList);
        System.out.println(shuffledList);
//        }


//        sendMail(userDataList);

        return "santa";
    }

    private Map<String, String> pairedMap = new HashMap<>();


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


    public static List<UserData> shuffleCollection(List<UserData> userDataList) {
        Collections.shuffle(userDataList);

        for (int i = 0; i < userDataList.size(); i++) {
            String emailFrom;
            String emailTo;

            if (i == (userDataList.size() - 1)) {
                emailFrom = userDataList.get(i).getEmail();
                emailTo = userDataList.get(0).getEmail();

                if (emailFrom.equals("email1") && emailTo.equals("email2")) {
                    shuffleCollection(userDataList);
                }
            } else {
                emailFrom = userDataList.get(i).getEmail();
                emailTo = userDataList.get(i + 1).getEmail();
            }

            if (emailFrom.equals("email1") && emailTo.equals("email2")) {
                shuffleCollection(userDataList);
            }
        }
        return userDataList;
    }

}
