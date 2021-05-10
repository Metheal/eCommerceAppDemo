package core.concretes;

import core.abstracts.EmailService;

public class EmailManager implements EmailService {

    @Override
    public void sendMail(String email, String message) {
        LogManager.successLog(email + " adresine mail gonderildi.\n" + message);
    }
}
