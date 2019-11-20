package SecretSanta.Santa.Model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewEmailService {
    private List<UserEmailName> userEmails = new ArrayList<>();

    public List<UserEmailName> findAll() {
        return this.userEmails;
    }

    public void save(UserEmailName userEmail) {
        long id = userEmails.size() + 1;
        userEmail.setId(id);

        userEmails.add(userEmail);
    }

    public void update(UserEmailName userEmail) {
        long id = userEmail.getId();
        UserEmailName user = findOne(id);
        user.setName(userEmail.getName());
        user.setEmail(userEmail.getEmail());

        userEmails.set((int) id-1, user);

    }

    public UserEmailName findOne(long id) {
        return userEmails.stream()
                .filter(e -> e.getId() == id)
                .findAny().get();
    }
}
