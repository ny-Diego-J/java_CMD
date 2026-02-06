package Commands;

import filesystem.*;

public class CpCommand {
    /**
     * copys a File to a directory
     *
     * @param dir  directory to copy from
     * @param args args[1] = filename args[2] = directory to change to
     */
    public static void copyFile(Directory dir, String[] args) {
        if (args.length < 3) {
            Colors.printError("Invalid arguments");
            return;
        }
        File copyFile = dir.getFile(args[1]);
        Directory pasteDirectory = CdCommand.changeDirectory(dir, args[2]);
        pasteDirectory.createFile(copyFile.getName(), copyFile.getContent());
    }
}
