package core.concretes;

import core.abstracts.EmailConfirmationService;
import entities.concretes.User;

public class EmailConfirmationManager implements EmailConfirmationService {
    public User confirmEmail(User user, String confirmationCode) {
        if (confirmationCode.equals("1837837")) {
            user.setStatus(true);
            LogManager.successLog(user.getEmail() + " email adresi onaylanmistir");
        }
        return user;
    }
}
