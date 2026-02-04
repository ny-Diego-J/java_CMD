package Commands;

import filesystem.*;

import java.util.Scanner;

public class EditCommand {
    public static void editFile(Directory dir, String[] args) {
        Scanner sc = new Scanner(System.in);
        File entry = dir.getFile(args[1]);
        System.out.println("Enter new Text");
        String newText = sc.nextLine();
        entry.setContent(newText);
    }
}
