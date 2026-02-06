import filesystem.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import Commands.*;

public class JsonLoader {

    private static int i = 0;
    private static String json;

    private static String getString() throws IOException {
        String json = Files.readString(Path.of("Files.json"));
        return json;
    }

    public static Directory loadFromJson() throws IOException {
        json = getString();
        i = 0;
        skipWhitespace();
        return (Directory) readEntry(null);
    }

    private static Entry readEntry(Directory parent) {
        skipWhitespace();
        expect('{');

        String name = null;
        String user = null;
        String permission = null;
        String fileType = null;
        String content = null;

        Directory dir = null;

        while (true) {
            skipWhitespace();

            if (peek() == '}') {
                i++;
                break;
            }

            String key = readString();
            skipWhitespace();
            expect(':');
            skipWhitespace();

            switch (key) {
                case "name" -> name = readString();
                case "user" -> user = readString();
                case "permission" -> permission = readString();
                case "fileType" -> fileType = readString();
                case "content" -> content = readString();

                case "children" -> {
                    dir = new Directory(name, parent, user, permission);
                    readChildren(dir);
                }

                case "Parent", "lastModified" -> skipValue();
                default -> skipValue();
            }

            skipWhitespace();
            if (peek() == ',') i++;
        }

        if (dir != null) return dir;

        return new filesystem.File(name, parent, content);
    }

    private static void readChildren(Directory parent) {
        skipWhitespace();
        expect('[');

        while (true) {
            skipWhitespace();

            if (peek() == ']') {
                i++;
                break;
            }

            if (peek() == ',') {
                i++;
                skipWhitespace();
            }

            Entry child = readEntry(parent);
            parent.addEntry(child);
        }
    }

    private static String readString() {
        expect('"');
        StringBuilder sb = new StringBuilder();

        while (true) {
            char c = json.charAt(i++);
            if (c == '"') break;
            if (c == '\\') {
                char next = json.charAt(i++);
                switch (next) {
                    case 'n' -> sb.append('\n');
                    case 'r' -> sb.append('\r');
                    case 't' -> sb.append('\t');
                    case '"' -> sb.append('"');
                    case '\\' -> sb.append('\\');
                    default -> sb.append(next);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static void skipWhitespace() {
        while (i < json.length() && Character.isWhitespace(json.charAt(i))) i++;
    }

    private static char peek() {
        return json.charAt(i);
    }

    private static void expect(char c) {
        if (json.charAt(i) != c)
            throw new RuntimeException(Colors.RED + "Expected '" + c + "' at " + i + Colors.RESET);
        i++;
    }

    private static void skipValue() {
        if (peek() == '"') readString();
        else {
            while (peek() != ',' && peek() != '}') i++;
        }
    }
}

