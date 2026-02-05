package Commands;

import filesystem.Directory;

public class MkdirCommand {
    /**
     * Creates directory
     * @param dir Directory to creat im
     * @param dirName Name of Directory
     */
    public static void makeDir(Directory dir, String[] dirName) {
        if (dirName.length < 2){
            System.out.println("Invalid arguments!");
            return;
        }
        dir.createDirectory(dirName[1]);
    }
}
