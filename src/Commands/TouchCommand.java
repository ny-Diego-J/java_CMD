package Commands;

import filesystem.*;

public class TouchCommand {
    /**
     * creates empty file in directory
     * @param dir directory to create file in
     * @param arguments aruments[1] = filename res flags
     */
    public static void createFile(Directory dir, String[] arguments){
        if(arguments.length < 2){
            System.out.println("Invalid arguments!");
            return;
        }
        dir.createFile(arguments[1], "");
    }
}
