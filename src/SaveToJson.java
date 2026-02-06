import filesystem.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class SaveToJson {

    /**
     * saves all dirs to a JSON
     * @param root root directory
     */
    public static void saveToJson(Directory root) {
        StringBuilder sb = new StringBuilder();

        writeEntry(sb, root, 0);

        writeToFile("Files.json", sb.toString());
    }

    /**
     * Writes the
     * @param fileName name of output file
     * @param content content of file
     */
    private static void writeToFile(String fileName, String content) {
        try {
            Files.writeString(Path.of(fileName), content, StandardCharsets.UTF_8);
            System.out.println("Successfully wrote to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while writing JSON: " + e.getMessage());
        }
    }

    /**
     * Writes the Entry for 1 File
     * @param sb Stringbuilder
     * @param entry entry to create JSON object
     * @param indent spacing from left
     */
    private static void writeEntry(StringBuilder sb, Entry entry, int indent) {
        indent(sb, indent).append("{\n");

        writeField(sb, indent + 2, "name", entry.getName(), true);
        writeField(sb, indent + 2, "Parent", entry.getParent() == null ? null : entry.getParent().getName(), true);
        writeField(sb, indent + 2, "user", entry.getUser(), true);
        writeField(sb, indent + 2, "permission", entry.getPermission(), true);
        writeField(sb, indent + 2, "lastModified", entry.getLastModified() == null ? null : entry.getLastModified().toString(), true);
        writeField(sb, indent + 2, "fileType", entry.getFileType() == null ? null : entry.getFileType().toString(), true);

        if (entry instanceof filesystem.File f) {
            writeField(sb, indent + 2, "content", f.getContent(), false);
            sb.append("\n");
        } else if (entry instanceof Directory dir) {
            indent(sb, indent + 2).append("\"children\": [\n");

            Iterator<Entry> it = dir.getChildren().values().iterator();
            while (it.hasNext()) {
                Entry child = it.next();
                writeEntry(sb, child, indent + 4);
                if (it.hasNext()) sb.append(",");
                sb.append("\n");
            }

            indent(sb, indent + 2).append("]\n");
        } else {
            indent(sb, indent + 2).append("\"children\": []\n");
        }

        indent(sb, indent).append("}");
    }

    /**
     *
     * @param sb
     * @param indent
     * @param key
     * @param value
     * @param comma
     */
    private static void writeField(StringBuilder sb, int indent, String key, String value, boolean comma) {
        indent(sb, indent).append("\"").append(escapeJson(key)).append("\": ");
        if (value == null) {
            sb.append("null");
        } else {
            sb.append("\"").append(escapeJson(value)).append("\"");
        }
        if (comma) sb.append(",");
        sb.append("\n");
    }

    /**
     * Gets indent for formatting
     * @param sb stringbuilder
     * @param spaces
     * @return the stringbuilder
     */
    private static StringBuilder indent(StringBuilder sb, int spaces) {
        for (int i = 0; i < spaces; i++) sb.append(' ');
        return sb;
    }

    /**
     * Fixes custom characters
     * @param s string to check
     * @return the fixed string
     */
    private static String escapeJson(String s) {
        if (s == null) return null;
        StringBuilder out = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\\' -> out.append("\\\\");
                case '"' -> out.append("\\\"");
                case '\n' -> out.append("\\n");
                case '\r' -> out.append("\\r");
                case '\t' -> out.append("\\t");
                case '\b' -> out.append("\\b");
                case '\f' -> out.append("\\f");
                default -> {
                    if (c < 0x20) {
                        out.append(String.format("\\u%04x", (int) c));
                    } else {
                        out.append(c);
                    }
                }
            }
        }
        return out.toString();
    }
}
