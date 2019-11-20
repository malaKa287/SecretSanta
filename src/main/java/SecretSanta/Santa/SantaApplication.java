package SecretSanta.Santa;

import SecretSanta.Santa.Model.NewEmailService;
import SecretSanta.Santa.Model.UserEmailName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SantaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SantaApplication.class, args);
    }

}
