package Commands;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginCommand {

    public static User login(String[] args, boolean isSudo) throws Exception {
        Scanner sc = new Scanner(System.in);
        List<User> users = parseUsers(loadJson());
        boolean a = true;
        String username = "";
        while (a) {
            System.out.print("Username: ");
            username = sc.nextLine();
            for (User u : users) {
                if (u.name.equals(username)) {
                    a = false;
                }
                if (username.equals("exit")) {
                    return null;
                }
            }
        }
        a = true;
        String password = "";
        while (a) {
            System.out.print("Password: ");
            password = sc.nextLine();
            for (User u : users) {
                if (u.name.equals(username) && u.password.equals(password)) {
                    System.out.println("Logged in as " + u.name + (u.doesSudo ? " (sudo)" : ""));
                    saveLogin(u);
                    return u;
                }
                if (password.equals("exit")) {
                    return null;
                }
            }
        }
        throw new Exception("Login failed");
    }


    private static void saveLogin(User user) {
        try {
            FileWriter myWriter = new FileWriter("login.txt");
            myWriter.write(getLoginString(user));
            myWriter.close();  // must close manually
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    private static String getLoginString(User user) {
        StringBuilder sc = new StringBuilder();

        sc.append(user.getName() + ";" + user.getPassword() + ";" + Instant.now());
        return sc.toString();
    }

    public static User autoLogin() throws IOException {

        String getLogins = Files.readString(Path.of("login.txt"));
        String[] login = getLogins.split(";");
        if (login.length < 3) {
            return null;
        }
        Instant time = Instant.parse(login[2]);
        Instant now = Instant.now();
        List<User> users = parseUsers(loadJson());
        Duration diffrenz = Duration.between(time, now);
        if (diffrenz.abs().toMinutes() < 10) {
            for (User u : users) {
                if (u.name.equals(login[0]) && u.password.equals(login[1])) {
                    System.out.println("Logged in as " + u.name + (u.doesSudo ? " (sudo)" : ""));
                    saveLogin(u);
                    return u;
                }
            }
        }
        return null;
    }


    private static String loadJson() throws IOException {
        InputStream is = LoginCommand.class
                .getClassLoader()
                .getResourceAsStream("logins.json");

        if (is == null) throw new IOException("logins.json not found in src");

        return new String(is.readAllBytes());
    }

    private static List<User> parseUsers(String json) {
        List<User> users = new ArrayList<>();

        json = json.replace("[", "")
                .replace("]", "")
                .trim();

        String[] objs = json.split("\\},\\s*\\{");

        for (String obj : objs) {
            obj = obj.replace("{", "").replace("}", "").replace("\"", "");

            User u = new User();
            u.name = getValue(obj, "name");
            u.password = getValue(obj, "password");
            u.doesSudo = Boolean.parseBoolean(getValue(obj, "doesSudo"));

            users.add(u);
        }

        return users;
    }

    private static String getValue(String obj, String key) {
        String[] parts = obj.split(",");
        for (String p : parts) {
            String[] kv = p.split(":", 2);
            if (kv[0].trim().equals(key)) {
                return kv[1].trim();
            }
        }
        return "";
    }
}
