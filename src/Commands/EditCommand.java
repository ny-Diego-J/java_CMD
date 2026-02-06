package Commands;

import filesystem.*;

import java.util.Scanner;

public class EditCommand {
    /**
     * Edits a File
     * @param dir Directory of file
     * @param args args[1] is Filename
     */
    public static void editFile(Directory dir, String[] args) {
        if( args.length <  2) {
            Colors.printError("Invalid arguments");
            return;
        }
        Scanner sc = new Scanner(System.in);
        File entry = dir.getFile(args[1]);
        System.out.println("Enter new Text");
        String newText = sc.nextLine();
        entry.setContent(newText);
    }
}
