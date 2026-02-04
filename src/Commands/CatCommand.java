package Commands;

import filesystem.*;

public class CatCommand {
    public static void getContent(Directory dir, String[] args){
        File entry = dir.getFile(args[1]);
        System.out.println(entry.getContent());
    }
}
