package Commands;

import filesystem.*;

public class CpCommand {
    public static void copyFile(Directory dir, String[] args){
        if (args.length < 3){
            System.out.println("Invalid arguments");
            return;}
        File copyFile = dir.getFile(args[1]);
        Directory pasteDirectory = CdCommand.changeDirectory(dir, args[2]);
        pasteDirectory.createFile(copyFile.getName(), copyFile.getContent());
    }
}
