package SecretSanta.Santa.Service;

import SecretSanta.Santa.Model.Mail;
import SecretSanta.Santa.Model.UserData;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    public void sendEmail(Mail mail) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

//            helper.addAttachment("santa.jpg", new ClassPathResource("files/email-template/santa.jpg"));

            Template t = freemarkerConfig.getTemplate("secret santa.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
//            helper.setFrom(mail.getFrom());

            emailSender.send(message);
        } catch (Exception e) {
            System.out.println("!!!!!!!!!!!!!!!! sendSimpleMessage exception");
            e.printStackTrace();
        }
    }

    public void buildEmail(List<UserData> shuffledList){
        Mail mail = new Mail();

        for (int i = 0; i < shuffledList.size(); i++) {
            String emailTo;
            String recipientName;
            String emailToName;

            if (i == (shuffledList.size() - 1)) {
                emailTo = shuffledList.get(i).getEmail();
                emailToName = shuffledList.get(i).getName();
                recipientName = shuffledList.get(0).getName();
            } else {
                emailTo = shuffledList.get(i).getEmail();
                recipientName = shuffledList.get(i + 1).getName();
                emailToName = shuffledList.get(i).getName();
            }

            mail.setTo(emailTo);
            mail.setSubject("santa");

            Map<String, String> model = new HashMap();
            model.put("name", emailToName);
            model.put("recipient", recipientName);
            model.put("price", "200");

            mail.setModel(model);
            sendEmail(mail);
        }

    }
}
