package Commands;

import filesystem.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TreeCommand {
    public static void tree(Directory root) {
        System.out.println(root.getName());
        printDir(root, "");
    }

    private static void printDir(Directory dir, String prefix) {
        List<Entry> entries = new ArrayList<>(dir.getChildren().values());

        entries.sort(Comparator
                .comparing((Entry e) -> !(e instanceof Directory)) // false (dirs) zuerst
                .thenComparing(e -> e.getName().toLowerCase())
        );

        for (int i = 0; i < entries.size(); i++) {
            Entry e = entries.get(i);
            boolean last = (i == entries.size() - 1);

            String branch = last ? "└── " : "├── ";
            System.out.println(prefix + branch + e.getName());

            if (e instanceof Directory childDir) {
                String nextPrefix = prefix + (last ? "    " : "│   ");
                printDir(childDir, nextPrefix);
            }
        }
    }
}