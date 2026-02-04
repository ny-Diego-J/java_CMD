package Commands;

import filesystem.*;

public class RmCommand {

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
            System.out.println("Error: '" + targetName + "' not found");
            return;
        }

        if (!canDeleteFromDirectory(dir, currentUser)) {
            System.out.println("Error: User doesn't have permission to remove " + target.getName());
            return;
        }

        if (target instanceof Directory d && !d.getChildren().isEmpty() && !flags.equals("-rf")) {
            System.out.println("Error: Directory not empty: " + target.getName());
            return;
        }


        dir.removeEntry(target.getName());
        System.out.println("Successfully removed " + target.getName());
    }

    private static boolean canDeleteFromDirectory(Directory dir, User currentUser) {
        if (currentUser != null && currentUser.doesSudo) return true;

        String p = dir.getPermission();

        boolean isOwner = currentUser != null && dir.getUser().equals(currentUser.getName());

        if (isOwner) {
            return p.charAt(2) == 'w' && p.charAt(3) == 'x';
        } else if (currentUser != null) {
            return p.charAt(5) == 'w' && p.charAt(6) == 'x';
        } else {
            return p.charAt(8) == 'w' && p.charAt(9) == 'x';
        }
    }
}
