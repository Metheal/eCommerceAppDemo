package core.concretes;

public class LogManager {

    public static void log(String message) {
        System.out.println(message);
    }

    public static void errorLog(String message) {
        System.out.println("Islem basarisiz oldu: " + message);
    }

    public static void successLog(String message) {
        System.out.println("Islem basarili: " + message);
    }
}
