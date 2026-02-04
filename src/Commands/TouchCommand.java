package Commands;

import filesystem.*;

public class TouchCommand {
    public static void createFile(Directory dir, String[] arguments){
        if(arguments.length < 2){
            System.out.println("Invalid arguments!");
            return;
        }
        dir.createFile(arguments[1], "");
    }
}
