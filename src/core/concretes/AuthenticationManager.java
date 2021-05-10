package core.concretes;

import business.abstracts.UserService;
import core.abstracts.EmailConfirmationService;
import core.abstracts.InMemoryAuthenticationService;
import entities.concretes.User;

public class AuthenticationManager implements InMemoryAuthenticationService {
    private UserService userService;
    private EmailConfirmationService emailConfirmationService;

    public AuthenticationManager(UserService userService, EmailConfirmationService emailConfirmationService) {
        this.userService = userService;
        this.emailConfirmationService = emailConfirmationService;
    }

    @Override
    public User login(String email, String password) {
        User loggedInUser = null;
        for (var user : userService.getAll()) {
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                loggedInUser = user;
            }
        }
        if (loggedInUser != null) {
            LogManager.successLog("Giris yapildi. hosgeldin " + loggedInUser.getFirstName());
        } else {
            LogManager.errorLog("Giris bilgileriniz yanlis, lutfen kontrol edin");
        }
        return loggedInUser;
    }

    @Override
    public User register(User user) {
        userService.add(user);
        return this.login(user.getEmail(), user.getPassword());
    }

    @Override
    public void confirmEmail(User user, String confirmationCode) {
        User userToUpdate = emailConfirmationService.confirmEmail(user, confirmationCode);
        if (userToUpdate.getStatus()) {
            userService.update(userToUpdate);
        } else {
            LogManager.errorLog("Dogrulama kodunuz yanlis");
        }
    }
}
