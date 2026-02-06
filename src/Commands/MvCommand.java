package Commands;

import filesystem.*;

public class MvCommand {
    /**
     * Moves a file into another folder
     * @param dir Directory of the file
     * @param args args 1 and 2 are the file and the new path
     */
    public static void moveFile(Directory dir, String[] args) {
        if (args.length < 3){
            System.out.println("Invalid arguments!");
            return;
        }
        File moveFile = dir.getFile(args[1]);
        Directory newParent = CdCommand.changeDirectory(dir, args[2]);
        Directory oldParent = moveFile.getParent();

        oldParent.getChildren().remove(moveFile.getName());
        moveFile.setParent(newParent);
        newParent.getChildren().put(moveFile.getName(), moveFile);

    }
}
