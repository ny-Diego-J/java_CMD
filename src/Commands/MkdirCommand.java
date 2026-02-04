package Commands;

import filesystem.Directory;

public class MkdirCommand {
    public static void makeDir(Directory dir, String[] dirName) {
        if (dirName.length < 2){
            System.out.println("Invalid arguments!");
            return;
        }
        dir.createDirectory(dirName[1]);
    }
}
