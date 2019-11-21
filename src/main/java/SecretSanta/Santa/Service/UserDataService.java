package SecretSanta.Santa.Service;

import SecretSanta.Santa.Model.UserData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDataService {
    private List<UserData> userEmails = new ArrayList<>();

    public List<UserData> findAll() {
        return this.userEmails;
    }

    public void save(UserData userEmail) {
        long id = userEmails.size() + 1;
        userEmail.setId(id);

        userEmails.add(userEmail);
    }

    public void update(UserData userEmail) {
        long id = userEmail.getId();
        UserData user = findOne(id);
        user.setName(userEmail.getName());
        user.setEmail(userEmail.getEmail());

        userEmails.set((int) (id-1), user);

    }

    public void delete(long id){
        int index = (int) (id - 1);
        userEmails.remove(index);
    }

    public UserData findOne(long id) {
        return userEmails.stream()
                .filter(e -> e.getId() == id)
                .findAny().get();
    }
}
