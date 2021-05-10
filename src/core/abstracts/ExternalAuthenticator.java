package core.abstracts;

public interface ExternalAuthenticator extends AuthenticationService {
    void register(String email, String password);
}
