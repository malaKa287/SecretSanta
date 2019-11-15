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

        for (int i = 0; i < 100; i++) {
            System.out.println("+++++ " + i);
            System.out.println("final random: " + randomPair());
        }


        return "santa";
    }

    public List<Integer> randomPair() {
        System.out.println("!!!!!!!!");
        List<Integer> integers = IntStream.range(1, email.getEmailsMap().size() + 1)
                .boxed()
                .collect(Collectors.toList());
//        List<Integer> random = new ArrayList();
//                random.add(1);
//                random.add(2);
//                random.add(3);

        Collections.shuffle(integers);

        Map map = email.getEmailsMap();

        List<Integer> random = new ArrayList<>(integers);

        System.out.println("random: " + random);

        for (int i = 0; i < random.size(); i++) {
            try {
                if (map.get(random.get(i)).equals("email1") && map.get(random.get(i + 1)).equals("email2")) {
                    randomPair();
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("IndexOutOfBoundsException");

                if (map.get(random.get(random.size() - 1)).equals("email1") && map.get(random.get(0)).equals("email2")) {
                    randomPair();
                }
            }
        }

        System.out.println("final: " + random);
        System.out.println("-------------");

        return random;
    }

    public static List<Integer> shuffleCollection(List<Integer> list){

        return new ArrayList<>();
    }
}
