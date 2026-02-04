package Commands;

import filesystem.Directory;

public class MkDirCommand {
    public static void makeDir(Directory dir, String dirName) {
        dir.createDirectory(dirName);
    }
}
