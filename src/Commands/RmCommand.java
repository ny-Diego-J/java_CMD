package Commands;

import filesystem.*;

public class RmCommand {

    /**
     * Remove a file or a directory
     * @param dir current directory
     * @param args flags and file/directory name
     * @param currentUser User to get permissions
     */
    public static void remove(Directory dir, String[] args, User currentUser) {
        if (args.length < 2) {
            System.out.println("Usage: rm <name>");
            return;
        }

        String targetName = args[1];
        String flags = "";
        if (args.length < 3) {
        } else {flags = args[2];}


        Entry target = dir.getChildren().get(targetName);
        if (target == null) {
            Colors.printError("Error: '" + targetName + "' not found");
            return;
        }

        if (!canDeleteFromDirectory(dir, currentUser)) {
            Colors.printError("Error: User doesn't have permission to remove " + target.getName());
            return;
        }

        if (target instanceof Directory d && !d.getChildren().isEmpty() && !flags.equals("-rf")) {
            Colors.printError("Error: Directory not empty: " + target.getName());
            return;
        }


        dir.removeEntry(target.getName());
        System.out.println("Successfully removed " + target.getName());
    }

    /**
     * check if user is enabled to
     * @param dir
     * @param currentUser
     * @return
     */
    private static boolean canDeleteFromDirectory(Directory dir, User currentUser) {
        if (currentUser != null && currentUser.doesSudo) return true;

        String permission = dir.getPermission();

        boolean isOwner = currentUser != null && dir.getUser().equals(currentUser.getName());

        if (isOwner) {
            return permission.charAt(2) == 'w' && permission.charAt(3) == 'x';
        } else if (currentUser != null) {
            return permission.charAt(5) == 'w' && permission.charAt(6) == 'x';
        } else {
            return permission.charAt(8) == 'w' && permission.charAt(9) == 'x';
        }
    }
}
