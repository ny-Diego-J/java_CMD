package Commands;

import filesystem.*;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Comparator;

public class LsCommand {

    private boolean printAll = false;
    private boolean doOrder = false;
    private boolean doFormat = false;

    private static ArrayList<Entry> listFile(Directory dir) {
        ArrayList<Entry> list = new ArrayList<>();
        for (Entry e : dir.getChildren().values()) {
            list.add(e);
        }
        return list;
    }

    /**
     * Menu to list Directories
     *
     * @param dir  Current Directory
     * @param args Arguments (can vary based on input)
     */
    public void listDirectory(Directory dir, String[] args) {

        switch (args.length) {
            case 1 -> printDirectory(dir);
            case 2 -> checkDir(dir, args);
            case 3 -> allThree(dir, args);
            default -> Colors.printError("Invalid argument");
        }

    }

    /**
     * Check if input is flag or dir
     *
     * @param dir  Current directory
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
     *
     * @param dir  reference directory
     * @param args new directory and flags
     */
    private void allThree(Directory dir, String[] args) {
        parseFlags(args[2]);
        Directory child = CdCommand.changeDirectory(dir, args[1]);
        printDirectory(child);
    }

    /**
     * Prints directory accounting flags
     *
     * @param dir directory to print
     */
    private void printDirectory(Directory dir) {
        ArrayList<Entry> entries = listFile(dir);
        if (doOrder) {
            entries.sort(Comparator.comparing(Entry::getLastModified));
        }

        entries.sort(Comparator.comparing(e -> e.getName().toLowerCase()));

        if (printAll) {
            if (doFormat) {
                printPermission(dir.getPermission());
                System.out.println("\t" + Colors.YELLOW + dir.getUser() + "\t" + Colors.BLUE + dir.getFormattedLastModified() + "\t" + "." + Colors.RESET);
                Directory parent = dir.getParent();
                if (parent != null) {
                    printPermission(parent.getPermission());
                    System.out.println("\t" + Colors.YELLOW + parent.getUser() + "\t" + Colors.BLUE + parent.getFormattedLastModified() + "\t" + ".." + Colors.RESET);
                } else {
                    printPermission(dir.getPermission());
                    System.out.println("\t" + Colors.YELLOW + dir.getUser() + "\t" + Colors.BLUE + dir.getFormattedLastModified() + "\t" + ".." + Colors.RESET);
                }
            } else {
                System.out.print(". ");
                System.out.print(".. ");
            }
        }

        for (Entry entry : entries) {
            if (doFormat) {
                printPermission(dir.getPermission());
                System.out.println("\t" + Colors.YELLOW + entry.getUser() + "\t" + Colors.BLUE + entry.getFormattedLastModified() + "\t" + (entry.getFileType() == FileType.FILE ? Colors.RESET: Colors.BLUE) + entry.getName() + Colors.RESET);
            } else {
                System.out.print(entry.getName() + " ");
            }
        }
        System.out.println();
    }

    /**
     * Prints permission in right colors
     *
     * @param permission permission string
     */
    private void printPermission(String permission) {
        for (int i = 0; i < permission.length(); i++) {
            switch (permission.charAt(i)) {
                case 'd' -> System.out.print(Colors.BLUE + "d" + Colors.RESET);
                case 'r' -> System.out.print(Colors.YELLOW + "r" + Colors.RESET);
                case 'w' -> System.out.print(Colors.RED + "w" + Colors.RESET);
                case 'x' -> System.out.print(Colors.GREEN + "x" + Colors.RESET);
                case '-' -> System.out.print(Colors.RESET + "-");
            }
        }
    }

    /**
     * check if the input activates the flags
     *
     * @param flags String of flags
     */
    private void parseFlags(String flags) {

        for (int i = 1; i < flags.length(); i++) {
            switch (flags.charAt(i)) {
                case 'l' -> doFormat = true;
                case 'a' -> printAll = true;
                case 't' -> doOrder = true;
            }
        }
    }
}
