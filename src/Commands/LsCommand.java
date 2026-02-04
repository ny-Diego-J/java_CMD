package Commands;

import filesystem.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LsCommand {

    public static void listDirectory(Directory dir, String[] rawArguments) {
        StringBuilder sb = new StringBuilder();
        boolean format = false;
        boolean all = false;

        for (int i = 1; i < rawArguments.length; i++) {
            sb.append(rawArguments[i]);
        }

        String argument = sb.toString();

        if (argument.contains("-") && argument.contains("l")) {
            format = true;
        }
        if (argument.contains("-") && argument.contains("a")) {
            all = true;
        }

        printList(dir, format, all);
    }

    private static void printList(Directory dir, boolean format, boolean all) {
        ArrayList<Entry> entries = listFile(dir);

        entries.sort(Comparator.comparing(e -> e.getName().toLowerCase()));

        if (all) {
            if (format) {
                System.out.println(dir.getPermission() + "\t" + dir.getUser() + "\t" + dir.getFormattedLastModified() + "\t" + ".");
                Directory parent = dir.getParent();
                if (parent != null) {
                    System.out.println(parent.getPermission() + "\t" + parent.getUser() + "\t" + parent.getFormattedLastModified() + "\t" + "..");
                } else {
                    // falls root kein parent hat
                    System.out.println(dir.getPermission() + "\t" + dir.getUser() + "\t" + dir.getFormattedLastModified() + "\t" + "..");
                }
            } else {
                System.out.print(". ");
                System.out.print(".. ");
            }
        }

        for (Entry entry : entries) {
            if (format) {
                System.out.println(entry.getPermission() + "\t" + entry.getUser() + "\t" + entry.getFormattedLastModified() + "\t" + entry.getName());
            } else {
                System.out.print(entry.getName() + " ");
            }
        }
        System.out.println();
    }

    private static ArrayList<Entry> listFile(Directory dir) {
        ArrayList<Entry> list = new ArrayList<>();
        for (Entry e : dir.getChildren().values()) {
            list.add(e);
        }
        return list;
    }
}
