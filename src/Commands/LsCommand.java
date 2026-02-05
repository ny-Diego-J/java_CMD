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

    /**
     * Menu to list Directories
     * @param dir Current Directory
     * @param args Arguments (can vary based on input)
     */
    public void listDirectory(Directory dir, String[] args) {

        switch (args.length) {
            case 1 -> printDirectory(dir);
            case 2 -> checkDir(dir, args);
            case 3 -> allThree(dir, args);
            default -> System.out.println("Invalid argument");
        }

    }

    /**
     * Check if input is flag or dir
     * @param dir Current directory
     * @param args Agrs[1] gets checkt if dir or flag
     */
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

    /**
     * Prints inputted directory with flags
     * @param dir reference directory
     * @param args new directory and flags
     */
    private void allThree(Directory dir, String[] args) {
        parseFlags(args[2]);
        Directory child = CdCommand.changeDirectory(dir, args[1]);
        printDirectory(child);
    }

    /**
     * Prints directory accounting flags
     * @param dir
     */
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
