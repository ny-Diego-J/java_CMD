package Commands;

import filesystem.*;

public class ChmodCommand {
    public static void changePermission(User user, Directory dir, String[] args) {
        if (args.length < 3) {
            System.out.println("Invalid arguments!");
            return;
        }
        if (user == null) {
            System.out.println("You don't have permission to use this command!");
            return;
        }
        if (!user.doesSudo) {
            System.out.println("You dom't have permission to use this command!");
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
            System.out.println("This ins't a valid directory/file");
            return;
        }


        switch (args[1].charAt(0)) {
            case '+' -> addPermission(change, args[1]);
            case '-' -> removePermission(change, args[1]);
            default -> setPermissionByNumber(change, args[1]);
        }


    }

    private static void addPermission(Entry e, String permission) {
        char permissionChar = permission.charAt(1);
        String rawPermission = e.getPermission();
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
        e.setPermission(rawPermission);
    }

    private static void removePermission(Entry e, String permission) {
        char permissionChar = permission.charAt(1);
        String rawPermission = e.getPermission();
                for (int i = 0; i < rawPermission.length(); i++) {
                    if (rawPermission.charAt(i) == permissionChar) {
                        rawPermission = rawPermission.substring(0, i) + '-' + rawPermission.substring(i + 1);
                    }
                }
        e.setPermission(rawPermission);

    }

    private static void setPermissionByNumber(Entry e, String permission) {
        int user = permission.charAt(0) - '0';
        int group = permission.charAt(1) - '0';
        int all = permission.charAt(2) - '0';
        StringBuilder sc = new StringBuilder();
        sc.append(e.getPermission().charAt(0));
        sc.append(getPermissionLevel(user));
        sc.append(getPermissionLevel(group));
        sc.append(getPermissionLevel(all));
        e.setPermission(sc.toString());
    }

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
                System.out.println("Invalid input!");
                break;
            }
        }
        return "";
    }
}