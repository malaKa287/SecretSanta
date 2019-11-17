package SecretSanta.Santa.Controller;

import SecretSanta.Santa.Model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class EmailController {

    @Autowired
    Email email;

    @GetMapping("/sendEmail")
    public String sendEmail() {
        System.out.println(email.getEmailsMap());

        for (int i = 0; i < 100; i++){
            randomPair();
        }

        return "santa";
    }

    public List<Integer> randomPair() {
        List<Integer> randomList = IntStream.range(1, email.getEmailsMap().size() + 1)
                .boxed()
                .collect(Collectors.toList());

        Map<String, String> pairMap = new HashMap<>();
        Map<String, String> emailsMap = email.getEmailsMap();

        shuffleCollection(randomList, emailsMap);

        System.out.println("randomList: " + randomList);

        //generate paired map
        for (int i = 0; i < randomList.size(); i++){
            try {
                pairMap.put(emailsMap.get(randomList.get(i).toString()), emailsMap.get(randomList.get(i + 1).toString()));
            } catch (IndexOutOfBoundsException e){
                pairMap.put(emailsMap.get(randomList.get(randomList.size() - 1).toString()), emailsMap.get(randomList.get(0).toString()));
            }
        }

        System.out.println("pairMap: " + pairMap);
        System.out.println("-------------");

        return randomList;
    }

    public static List<Integer> shuffleCollection(List<Integer> randomList, Map emailsMap) {
        Collections.shuffle(randomList);

        for (int i = 0; i < randomList.size(); i++) {
            try {
                if (emailsMap.get(randomList.get(i).toString()).equals("email1") && emailsMap.get(randomList.get(i + 1).toString()).equals("email2")) {
                    shuffleCollection(randomList, emailsMap);
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("IndexOutOfBoundsException");

                if (emailsMap.get(randomList.get(randomList.size() - 1).toString()).equals("email1") && emailsMap.get(randomList.get(0).toString()).equals("email2")) {
                    shuffleCollection(randomList, emailsMap);
                }
            }
        }
        return randomList;
    }
}
