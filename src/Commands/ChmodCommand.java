package Commands;

import filesystem.*;

public class ChmodCommand {
    /**
     * Changes the permission of an Entry
     * @param user current User to check for permission
     * @param dir Dir where the Entry is
     * @param args arguments;
     *             args[1] = what to change
     *             args[2] = Entry to change
     */
    public static void changePermission(User user, Directory dir, String[] args) {
        if (args.length < 3) {
            Colors.printError("Invalid arguments");
            return;
        }
        if (user == null) {
            Colors.printError("You don't have permission to use this command!");
            return;
        }
        if (!user.doesSudo) {
            Colors.printError("You don't have permission to use this command!");
            return;
        }

        String permission = "";
        Entry change = null;
        for (Entry e : dir.getChildren().values()) {
            if (e.getName().equals(args[2])) {
                permission = e.getPermission();
                change = e;
                break;
            }
        }
        if (change == null) {
            Colors.printError("This ins't a valid directory/file");
            return;
        }


        switch (args[1].charAt(0)) {
            case '+' -> addPermission(change, args[1]);
            case '-' -> removePermission(change, args[1]);
            default -> setPermissionByNumber(change, args[1]);
        }


    }

    /**
     * add either r, w or x to Entry
     * @param entry Entry to change
     * @param permission what to change (r, w or x)
     */
    private static void addPermission(Entry entry, String permission) {
        char permissionChar = permission.charAt(1);
        String rawPermission = entry.getPermission();
        switch (permissionChar) {
            case 'r':
                for (int i = 0; i < rawPermission.length(); i++) {
                    if (rawPermission.charAt(i) == '-') {
                        if (i == 1 || i == 4 || i == 7) {
                            rawPermission = rawPermission.substring(0, i) + 'r' + rawPermission.substring(i + 1);
                        }
                    }
                }
                break;
            case 'w':
                for (int i = 0; i < rawPermission.length(); i++) {
                    if (rawPermission.charAt(i) == '-') {
                        if (i == 2 || i == 5 || i == 8) {
                            rawPermission = rawPermission.substring(0, i) + 'w' + rawPermission.substring(i + 1);
                        }
                    }
                }
                break;
            case 'x':
                for (int i = 0; i < rawPermission.length(); i++) {
                    if (rawPermission.charAt(i) == '-') {
                        if (i == 3 || i == 5 || i == 8) {
                            rawPermission = rawPermission.substring(0, i) + 'r' + rawPermission.substring(i + 1);
                        }
                    }
                }
                break;
        }
        entry.setPermission(rawPermission);
    }

    /**
     * remove either r, w or x to Entry
     * @param entry Entry to change
     * @param permission what to change (r, w or x)
     */
    private static void removePermission(Entry entry, String permission) {
        char permissionChar = permission.charAt(1);
        String rawPermission = entry.getPermission();
                for (int i = 0; i < rawPermission.length(); i++) {
                    if (rawPermission.charAt(i) == permissionChar) {
                        rawPermission = rawPermission.substring(0, i) + '-' + rawPermission.substring(i + 1);
                    }
                }
        entry.setPermission(rawPermission);

    }

    /**
     * change permission with number from 1-7
     * @param entry Entry to change
     * @param permission String of numbers to change
     */
    private static void setPermissionByNumber(Entry entry, String permission) {
        int user = permission.charAt(0) - '0';
        int group = permission.charAt(1) - '0';
        int all = permission.charAt(2) - '0';
        StringBuilder sc = new StringBuilder();
        sc.append(entry.getPermission().charAt(0));
        sc.append(getPermissionLevel(user));
        sc.append(getPermissionLevel(group));
        sc.append(getPermissionLevel(all));
        entry.setPermission(sc.toString());
    }

    /**
     * Get a String from a number from
     * @param input number 1-7
     * @return returns permission string
     */
    private static String getPermissionLevel(int input) {
        switch (input) {
            case 0 -> {
                return "---";
            }
            case 1 -> {
                return "--x";
            }
            case 2 -> {
                return "-w-";
            }
            case 3 -> {
                return "-wx";
            }
            case 4 -> {
                return "r--";
            }
            case 5 -> {
                return "r-x";
            }
            case 6 -> {
                return "rw-";
            }
            case 7 -> {
                return "rwx";
            }
            default -> {
                Colors.printError("Invalid input");
            }
        }
        return "";
    }
}