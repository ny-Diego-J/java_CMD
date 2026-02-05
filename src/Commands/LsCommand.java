package Commands;

import filesystem.*;

import java.util.ArrayList;
import java.util.Comparator;

public class LsCommand {

    private boolean all = false;
    private boolean order = false;
    private boolean format = false;

    private static ArrayList<Entry> listFile(Directory dir) {
        ArrayList<Entry> list = new ArrayList<>();
        for (Entry e : dir.getChildren().values()) {
            list.add(e);
        }
        return list;
    }

    public void listDirectory(Directory dir, String[] rawArguments) {
        StringBuilder sb = new StringBuilder();

        switch (rawArguments.length) {
            case 1 -> printDirectory(dir);
            case 2 -> checkDir(dir, rawArguments);
            case 3 -> allThree(dir, rawArguments);
            default -> System.out.println("Invalid argument");
        }

    }

    private void checkDir(Directory dir, String[] args) {
        if (args[1].charAt(0) != '-') {
            for (Entry e : dir.getChildren().values()) {
                if (e.getName().equalsIgnoreCase(args[1]) && e.getFileType().equals(FileType.DIRECTORY)) {
                    printDirectory((Directory) e);
                }
            }
        } else {
            parseFlags(args[1]);
            printDirectory(dir);
        }
    }

    private void allThree(Directory dir, String[] args) {
        parseFlags(args[2]);
        Directory child = CdCommand.changeDirectory(dir, args[1]);
        printDirectory(child);
    }

    private void printDirectory(Directory dir) {
        ArrayList<Entry> entries = listFile(dir);
        if (order) {
            entries.sort(Comparator.comparing(Entry::getLastModified));
        }

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

    private void parseFlags(String flags) {

        for (int i = 1; i < flags.length(); i++) {
            switch (flags.charAt(i)) {
                case 'l' -> format = true;
                case 'a' -> all = true;
                case 't' -> order = true;
            }
        }
    }
}
