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


    public void sendEmail(Mail mail) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template template = freemarkerConfig.getTemplate("secret santa.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());

            StringWriter writer = new StringWriter();
            template.process(mail.getModel(), writer);
            String strTemplate = writer.toString();

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(strTemplate, "text/html;  charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // add inline image attachments
            Map<String, byte[]> pictureMap = new HashMap();

            try {
                byte[]  santaPictureBytes = IOUtils.toByteArray(new ClassPathResource("files/email-template/santa.jpg").getInputStream());
                pictureMap.put("santa_picture", santaPictureBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

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

            message.setContent(multipart);

            emailSender.send(message);
        } catch (Exception e) {
            System.out.println("!!!!!!!!!!!!!!!! sendSimpleMessage exception");
            e.printStackTrace();
        }
    }

    public void buildEmail(List<UserData> shuffledList) {
        for (int i = 0; i < shuffledList.size(); i++) {
            Mail mail = new Mail();

            String emailTo;
            String recipientName;
            String senderName;

            if (i == (shuffledList.size() - 1)) {
                emailTo = shuffledList.get(i).getEmail();
                senderName = shuffledList.get(i).getName();
                recipientName = shuffledList.get(0).getName();
            } else {
                emailTo = shuffledList.get(i).getEmail();
                recipientName = shuffledList.get(i + 1).getName();
                senderName = shuffledList.get(i).getName();
            }

            mail.setTo(emailTo);
            mail.setRecipientName(senderName);
            mail.setRecipientName(recipientName);
            mail.setSubject("santa");

            Map<String, String> model = new HashMap();
            model.put("name", senderName);
            model.put("recipient", recipientName);
            model.put("price", "200");

            mail.setModel(model);

            sendEmail(mail);
        }
        // clear gmail folders
        deleteSentEmail();
    }

    public void deleteSentEmail() {
        try {
            Properties properties = new Properties();
            properties.put("mail.imap.host", "smtp.gmail.com");
            properties.put("mail.imap.port", "587");
            properties.put("mail.imap.starttls.enable", "true");

            Session session = Session.getInstance(properties);
            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", "Secret.Santa.EPAM2019@gmail.com", "asdasdaw12");

            Folder folder = store.getFolder("[Gmail]/Надіслані");
            Folder folderBucket = store.getFolder("[Gmail]/Кошик");
            Folder allEmails = store.getFolder("[Gmail]/Уся пошта");

            folder.open(Folder.READ_WRITE);
            folderBucket.open(Folder.READ_WRITE);
            allEmails.open(Folder.READ_WRITE);

            Message[] messages = folder.getMessages();
            Message[] messagesBucket = folderBucket.getMessages();
            Message[] allMessages = allEmails.getMessages();

            for (Message messageSent: messages) {
                messageSent.setFlag(Flags.Flag.DELETED, true);
            }
            for (Message messageBucket: messagesBucket) {
                messageBucket.setFlag(Flags.Flag.DELETED, true);
            }
            for (Message messageAll: allMessages) {
                messageAll.setFlag(Flags.Flag.DELETED, true);
            }

            folder.close(true);
            folderBucket.close(true);
            allEmails.close(true);
            store.close();

            System.out.println("Gmail account is cleared");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
