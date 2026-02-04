package Commands;

import filesystem.*;

public class TouchCommand {
    public static void createFile(Directory dir, String[] arguments){
        dir.createFile(arguments[1], "");
    }
}
