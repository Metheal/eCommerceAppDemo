package core.concretes;

import business.abstracts.UserService;
import core.abstracts.ExternalAuthenticator;
import entities.concretes.User;

public class GoogleAuthenticator implements ExternalAuthenticator {
    UserService userService;

    public GoogleAuthenticator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void register(String email, String password) {
        if (checkEmail(email)) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName("Google");
            user.setLastName("User");
            userService.add(user);
        } else {
            LogManager.errorLog("Gecerli bir Google Mail adresi girin");
        }
    }

    private boolean checkEmail(String email) {
        return email.contains("@gmail");
    }
}
