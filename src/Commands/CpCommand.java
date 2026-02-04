package Commands;

import filesystem.*;

public class CpCommand {
    public static void copyFile(Directory dir, String[] args){
        File copyFile = dir.getFile(args[1]);
        Directory pasteDirectory = CdCommand.changeDirectory(dir, args[2]);
        pasteDirectory.createFile(copyFile.getName(), copyFile.getContent());
    }
}
