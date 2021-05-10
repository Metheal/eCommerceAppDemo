package core.abstracts;

import entities.concretes.User;

public interface EmailConfirmationService {
    User confirmEmail(User user, String confirmationCode);
}
