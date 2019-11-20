package SecretSanta.Santa.Controller;

import SecretSanta.Santa.Model.EmailModel;
import com.sun.mail.smtp.SMTPAddressFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class EmailController {

//    @Autowired
//    EmailModel emailModel;
    EmailModel emailModel = new EmailModel();

    @Autowired
    JavaMailSender javaMailSender;

    private Map<String,String> pairedMap = new HashMap<>();

    @GetMapping("/sendEmail")
    public String sendEmail() {
        System.out.println(emailModel.getEmailsMap());

        randomPair();

        System.out.println(pairedMap);

        sendMail();

        return "santa";
    }

    public void sendMail() {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;


        for (Map.Entry<String, String> entry : pairedMap.entrySet()) {
            try {
                String emailFrom = entry.getKey();
                String emailTo = entry.getValue();

                helper = new MimeMessageHelper(message, true);
                helper.setTo(emailFrom);
                helper.setText("Ho ho ho, Santa is coming. " + emailTo + " already waiting for your gift.");
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

    public void randomPair() {
        List<Integer> randomList = IntStream.range(1, emailModel.getEmailsMap().size() + 1)
                .boxed()
                .collect(Collectors.toList());

        Map<String, String> emailsMap = emailModel.getEmailsMap();

        shuffleCollection(randomList, emailsMap);

        System.out.println("randomList: " + randomList);

        //generate paired map
        for (int i = 0; i < randomList.size(); i++) {
            try {
                String emailFrom = emailsMap.get(randomList.get(i).toString());
                String emailTo = emailsMap.get(randomList.get(i + 1).toString());

                pairedMap.put(emailFrom, emailTo);
            } catch (IndexOutOfBoundsException e) {
                String emailFrom = emailsMap.get(randomList.get(randomList.size() - 1).toString());
                String emailTo = emailsMap.get(randomList.get(0).toString());

                pairedMap.put(emailFrom, emailTo);
            }
        }

        System.out.println("pairMap: " + pairedMap);
        System.out.println("-------------");
    }

    public static List<Integer> shuffleCollection(List<Integer> randomList, Map<String, String> emailsMap) {
        Collections.shuffle(randomList);

        for (int i = 0; i < randomList.size(); i++) {
            try {
                String emailFrom = emailsMap.get(randomList.get(i).toString());
                String emailTo = emailsMap.get(randomList.get(i + 1).toString());

                if (emailFrom.equals("email1") && emailTo.equals("email2")) {
                    shuffleCollection(randomList, emailsMap);
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("IndexOutOfBoundsException");

                String emailFrom = emailsMap.get(randomList.get(randomList.size() - 1).toString());
                String emailTo = emailsMap.get(randomList.get(0).toString());

                if (emailFrom.equals("email1") && emailTo.equals("email2")) {
                    shuffleCollection(randomList, emailsMap);
                }
            }
        }
        return randomList;
    }
}
