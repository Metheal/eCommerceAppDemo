package business.entities;

import business.abstracts.UserService;
import core.abstracts.EmailService;
import core.concretes.LogManager;
import dataAccess.business.UserDao;
import entities.concretes.User;

import java.util.List;
import java.util.regex.Pattern;

public class UserManager implements UserService {
    private UserDao userDao;
    private EmailService emailService;

    public UserManager(UserDao userDao, EmailService emailService) {
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @Override
    public void add(User user) {
        if (!checkPasswordLength(user.getPassword())) {
            LogManager.errorLog("Parola en az 6 karakterden olusmalidir");
        } else if (!checkEmail(user.getEmail())) {
           LogManager.errorLog("Gecerli bir email adresi girin");
        } else if (!checkIfEmailIsAvailable(user.getEmail())) {
            LogManager.errorLog("Bu mail adresi alinmistir");
        } else if (!checkNameLength(user)) {
            LogManager.errorLog("Ad ve soyadin her biri en az 2 karakterden olusmalidir.");
        } else {
            user.setStatus(false);
            user.setId(this.userDao.getAll().size());
            this.userDao.add(user);
            LogManager.successLog("Kullanici basariyla olusturuldu");
            this.emailService.sendMail(user.getEmail(), "Lutfen asagidaki linke tiklayarak email adresinizi dogrulayin...\nhttp://example.com/confirmEmail?confirmationCode=1837837");
        }
    }

    @Override
    public void update(User user) {
        if (!checkPasswordLength(user.getPassword())) {
            LogManager.errorLog("Parola en az 6 karakterden olusmalidir");
        } else if (!checkEmail(user.getEmail())) {
            LogManager.errorLog("Gecerli bir email adresi girin");
        } else if (!checkNameLength(user)) {
            LogManager.errorLog("Ad ve soyadin her biri en az 2 karakterden olusmalidir.");
        } else {
            this.userDao.update(user);
            LogManager.successLog(user.getEmail() +  " kullanici basariyla guncellendi");
        }
    }

    @Override
    public void delete(User user) {
        this.userDao.delete(user);
    }

    @Override
    public User get(int id) {
        return this.userDao.get(id);
    }

    @Override
    public List<User> getAll() {
        return this.userDao.getAll();
    }

    private boolean checkPasswordLength(String password) {
        return password.length() >= 6;
    }

    private boolean checkEmail(String email) {
        return Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email).find();
    }

    private boolean checkIfEmailIsAvailable(String email) {
        for (var user : this.getAll()) {
            if (email.equals(user.getEmail())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkNameLength(User user) {
        return user.getFirstName().length() >= 2 && user.getLastName().length() >= 2;
    }
}
