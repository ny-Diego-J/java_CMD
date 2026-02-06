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
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LoginCommand {

    /**
     * Menu to log in the User
     *
     * @return the new User
     */
    public static User login() throws Exception {
        Scanner sc = new Scanner(System.in);
        List<User> users = parseUsers(loadJson());
        boolean isNotValidInput = true;
        String username = "";
        while (isNotValidInput) {
            System.out.print("Username: ");
            username = sc.nextLine();
            for (User u : users) {
                if (u.name.equals(username)) {
                    isNotValidInput = false;
                }
                if (username.equals("exit")) {
                    return null;
                }
            }
            if (isNotValidInput) {
                Colors.printError("User not found");

            }

        }
        isNotValidInput = true;
        String password = "";
        while (isNotValidInput) {
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
            Colors.printError("Invalid password");
        }
        throw new Exception(Colors.RED + "Login failed" + Colors.RESET);
    }


    /**
     * Logs last Login to login.txt
     *
     * @param user user to log
     */
    private static void saveLogin(User user) {
        try {
            FileWriter myWriter = new FileWriter("login.txt");
            myWriter.write(getLoginString(user));
            myWriter.close();  // must close manually
        } catch (IOException e) {
            Colors.printError("An error occurred.");
        }
    }

    /**
     * Make a String that gets Logged
     *
     * @param user User to Log
     * @return Resturns a String that is CSV formattet
     */
    private static String getLoginString(User user) {
        StringBuilder sb = new StringBuilder();

        sb.append(user.getName() + ";" + user.getPassword() + ";" + Instant.now());
        return sb.toString();
    }

    /**
     * Checks last login and logs him in if last login was before 10 min
     *
     * @return Returns a User to get Logged in
     */
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

    /**
     * Creates String from JSON
     *
     * @return String of Users
     */
    private static String loadJson() throws IOException {
        InputStream is = LoginCommand.class
                .getClassLoader()
                .getResourceAsStream("logins.json");

        if (is == null) throw new IOException(Colors.RED + "logins.json not found in src" + Colors.RESET);

        return new String(is.readAllBytes());
    }


    /**
     * Creates Users from JSON file
     *
     * @param json JSON string to get returnt
     * @return Returns Useres
     */
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

    /**
     * Get Value from Stringed JSON
     *
     * @param obj Objective
     * @param key Acctual Value
     * @return String
     */
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
