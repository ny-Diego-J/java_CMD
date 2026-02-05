package Commands;

import filesystem.*;

public class CatCommand {
    /**
     * Get content of file
     * @param dir directory witch the file is in
     * @param args filename and flags
     */
    public static void getContent(Directory dir, String[] args){
        File entry = dir.getFile(args[1]);
        System.out.println(entry.getContent());
    }
}
