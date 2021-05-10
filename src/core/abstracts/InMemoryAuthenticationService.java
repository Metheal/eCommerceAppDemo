package core.abstracts;

import entities.concretes.User;

public interface InMemoryAuthenticationService extends AuthenticationService {
    User login(String email, String password);
    User register(User user);
    void confirmEmail(User user, String confirmationCode);
}
