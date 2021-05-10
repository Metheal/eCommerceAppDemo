import business.abstracts.UserService;
import business.entities.UserManager;
import core.abstracts.EmailConfirmationService;
import core.abstracts.ExternalAuthenticator;
import core.abstracts.InMemoryAuthenticationService;
import core.concretes.AuthenticationManager;
import core.concretes.EmailConfirmationManager;
import core.concretes.EmailManager;
import core.concretes.GoogleAuthenticator;
import dataAccess.concretes.InMemoryUserDao;
import entities.concretes.User;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserManager(new InMemoryUserDao(), new EmailManager());
        EmailConfirmationService emailConfirmationService = new EmailConfirmationManager();
        InMemoryAuthenticationService inMemoryAuthenticationService = new AuthenticationManager(userService, emailConfirmationService);
        ExternalAuthenticator externalAuthenticator = new GoogleAuthenticator(userService);
        Scanner scanner = new Scanner(System.in);

        menu(inMemoryAuthenticationService, scanner, externalAuthenticator, userService);
    }

    private static void menu(InMemoryAuthenticationService inMemoryAuthenticationService, Scanner scanner, ExternalAuthenticator externalAuthenticator, UserService userService) {
        String mainOption;
        do {
            System.out.println("----------Menu----------");
            System.out.println("1. Uye ol");
            System.out.println("2. Google ile uye ol");
            System.out.println("3. Giris yap");
            System.out.println("4. Tum kullanicilari listele");
            System.out.println("0. Cikis");

            System.out.print("Lutfen devam etmek istediginiz secenegi girin: ");
            mainOption = scanner.nextLine();

            switch (mainOption) {
                case "1" -> {
                    System.out.println("----------Yeni uye olusturma----------");
                    User user = new User();
                    System.out.print("Adinizi girin: ");
                    user.setFirstName(scanner.nextLine());
                    System.out.print("Soyadinizi girin: ");
                    user.setLastName(scanner.nextLine());
                    System.out.print("Email adresinizi girin: ");
                    user.setEmail(scanner.nextLine());
                    System.out.print("Parolanizi girin: ");
                    user.setPassword(scanner.nextLine());
                    System.out.println();
                    User loggedInUser = inMemoryAuthenticationService.register(user);

                    String confirmationCode = scanner.nextLine();
                    inMemoryAuthenticationService.confirmEmail(loggedInUser, confirmationCode);
                }

                case "2" -> {
                    System.out.println("----------Google ile uye olun----------");
                    System.out.print("Email adresinizi girin: ");
                    String email = scanner.nextLine();
                    System.out.print("Parolanizi girin: ");
                    String password = scanner.nextLine();
                    externalAuthenticator.register(email, password);
                }

                case "3" -> {
                    System.out.println("----------Google ile uye olun----------");
                    System.out.print("Email adresinizi girin: ");
                    String email = scanner.nextLine();
                    System.out.print("Parolanizi girin: ");
                    String password = scanner.nextLine();
                    inMemoryAuthenticationService.login(email, password);
                }

                case "4" -> {
                    System.out.println("----------Tum kullanicilar----------");
                    for (var user : userService.getAll()) {
                        System.out.println(user.getId() + " " + user.getEmail() + " " + user.getFirstName() + " " + user.getLastName() + " " + user.getStatus());
                    }
                }
            }
        } while (!mainOption.equals("0"));
    }
}
