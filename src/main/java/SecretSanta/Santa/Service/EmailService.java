package SecretSanta.Santa.Service;

import SecretSanta.Santa.Model.Mail;
import SecretSanta.Santa.Model.UserData;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    byte[] santaPictureByte;

    {
        try {
            santaPictureByte = IOUtils.toByteArray(new ClassPathResource("files/email-template/santa.jpg").getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(Mail mail) {
        try {
//            MimeMessage message = emailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message,
//                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                    StandardCharsets.UTF_8.name());
//
////            helper.addAttachment("santa.jpg", new ClassPathResource("files/email-template/santa.jpg"));
//
            Template t = freemarkerConfig.getTemplate("secret santa.ftl");
//            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
//
//            helper.setTo(mail.getTo());
//            helper.setText(html, true);
//            helper.setSubject(mail.getSubject());
//            helper.setFrom(mail.getFrom());

            ////////
            StringWriter writer = new StringWriter();
            t.process(mail.getModel(), writer);

            String s = writer.toString();

            String userName = "Secret.Santa.EPAM2019@gmail.com";
            String password = "asdasdaw12";

            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.user", userName);
            properties.put("mail.password", password);

            // creates a new session with an authenticator
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            };
            Session session = Session.getInstance(properties, auth);

            // creates a new e-mail message
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(userName));
            msg.setRecipients(Message.RecipientType.TO, (InternetAddress.parse("ruslan_lelyk@epam.com")));
//            msg.setRecipients(Message.RecipientType.CC, (InternetAddress.parse(toCC)));
            msg.setSubject("santa");
            msg.setSentDate(new Date());
            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(s, "text/html;  charset=utf-8");

            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // adds inline image attachments
            Map<String, byte[]> pictureMap = new HashMap();

            pictureMap.put("santa_picture", santaPictureByte);


            if (pictureMap != null && pictureMap.size() > 0) {
                Set<String> setImageID = pictureMap.keySet();

                for (String contentId : setImageID) {
                    try {
                        MimeBodyPart imagePart = new MimeBodyPart();
                        imagePart.setHeader("Content-ID", "<" + contentId + ">");
                        imagePart.setDisposition(MimeBodyPart.INLINE);

                        InputStream is = new ByteArrayInputStream(pictureMap.get(contentId));
                        File imageFile = File.createTempFile(contentId, ".jpg");
                        BufferedImage image = ImageIO.read(is);
                        OutputStream os = new FileOutputStream(imageFile);
                        ImageIO.write(image, "jpg", os);
                        imagePart.attachFile(imageFile);
                        multipart.addBodyPart(imagePart);

                    } catch (Exception eeee) {
                        System.out.println("picture exception");
                    }
                }
            }

            msg.setContent(multipart);
            Transport.send(msg);

//            emailSender.send(message);
        } catch (Exception e) {
            System.out.println("!!!!!!!!!!!!!!!! sendSimpleMessage exception");
            e.printStackTrace();
        }
    }

    public void buildEmail(List<UserData> shuffledList) {
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
