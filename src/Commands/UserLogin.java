package Commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UserLogin {

    private String json;

    public UserLogin() {
        try {
            json = Files.readString(Path.of("logins.json"));
        } catch (IOException e) {
            System.out.println("Datei konnte nicht gelesen werden");
            json = "";
        }
    }

    public static String loadJson() {
        try {
            return Files.readString(Path.of("logins.json"));
        } catch (IOException e) {
            return "";
        }
    }
}