package Commands;

import filesystem.*;

import java.util.ArrayList;

public class PwdCommand {
    /**
     * Prints out the path
     * @param dir path to print out
     */
    public static void printPath(Directory dir) {
        System.out.println(dir.getPath());
    }
}
