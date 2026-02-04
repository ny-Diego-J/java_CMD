package Commands;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginCommand {

    public static User login(String[] args, boolean isSudo) throws Exception {
        if (args.length < 3) throw new Exception("Usage: login <name> <password>");
        int i = 0;
        if (isSudo) {
            i = 1;
        }

        String name = args[i + 1];
        String password = args[i + 2];

        List<User> users = parseUsers(loadJson());

        for (User u : users) {
            if (u.name.equals(name) && u.password.equals(password)) {
                System.out.println("Logged in as " + u.name + (u.doesSudo ? " (sudo)" : ""));
                return u;
            }
        }
        throw new Exception("Login failed");
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
