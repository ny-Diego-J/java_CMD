package Commands;

import filesystem.Directory;
import filesystem.Entry;

import java.io.IOException;


public class CdCommand {

    public static Directory changeDirectory(Directory dir, String newDirectory) {
        if (newDirectory == null || newDirectory.isBlank()) return dir;

        if (newDirectory.equals("..")) {
            return (dir.getParent() != null) ? dir.getParent() : dir;
        }

        String[] parts = formatString(newDirectory);

        Directory current = dir;
        if (newDirectory.startsWith("/")) {
            while (current.getParent() != null) current = current.getParent();
        }

        for (String part : parts) {
            if (part.equals(".")) continue;

            if (part.equals("..")) {
                if (current.getParent() != null) current = current.getParent();
                continue;
            }

            Entry child = current.getChildren().get(part);
            if (!(child instanceof Directory)) {
                System.out.println("Invalid directory: " + part);
                return dir;
            }

            current = (Directory) child;
        }

        return current;
    }



    private static String[] formatString(String rawString) {
        String[] parts = rawString.split("/");

        return java.util.Arrays.stream(parts).filter(s -> !s.isEmpty()).toArray(String[]::new);
    }
}
