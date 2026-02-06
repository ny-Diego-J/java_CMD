package filesystem;

import Commands.Colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Directory extends Entry {

    private Map<String, Entry> children = new HashMap<>();

    public Directory(String name, Directory parent) {
        super(name, parent, FileType.DIRECTORY);
    }

    public Directory(String name, Directory parent, String permission) {
        super(name, parent, FileType.DIRECTORY, permission);
    }

    public Directory(String name, Directory parent, String user, String permission) {
        super(name, parent, user, permission, FileType.DIRECTORY);
    }

    public Map<String, Entry> getChildren() {
        return children;
    }

    /**
     * creates a directory
     * @param name name of directory
     * @return directory
     */
    public Directory createDirectory(String name) {
        if (children.containsKey(name)) {
            Colors.printError("Name already exists");
            return null;
        }

        Directory dir = new Directory(name, this);
        children.put(name, dir);
        return dir;
    }

    /**
     * creates a directory that needs sudo rights
     * @param name name of directory
     * @param permission permission string r, w and x
     * @return directory
     */
    public Directory createSudoDirectory(String name, String permission) {
        if (children.containsKey(name)) {
            Colors.printError("Name already exists");
            return null;
        }
        Directory dir = new Directory(name, this, permission);
        children.put(name, dir);
        return dir;
    }

    /**
     * gets if a file current dir and returns it
     * @param name name of file
     * @return file if found else just null
     */
    public File getFile(String name) {
        Entry e = this.getChildren().get(name);
        File entry = null;
        if (e instanceof File) {
            entry = (File) e;
        }

        if (entry == null) {
            Colors.printError("File not found");
            return null;
        } else {
            return entry;
        }
    }

    public Directory getDirectory(String name) {
        Entry e = this.getChildren().get(name);
        Directory entry = null;
        if (e instanceof Directory) {
            entry = (Directory) e;
        }

        if (entry == null) {
            Colors.printError("Directory not found");
            return null;
        } else {
            return entry;
        }
    }

    /**
     * gets current path
     * @return returns path a string
     */
    public String getPath() {
        ArrayList<String> parts = new ArrayList<>();

        Directory cur = this;
        while (cur != null) {
            String name = cur.getName();
            if (name != null && !name.equals("/") && !name.isBlank()) {
                parts.add(name);
            }
            cur = cur.getParent();
        }
        StringBuilder sb = new StringBuilder("/");
        for (int i = parts.size() - 1; i >= 0; i--) {
            sb.append(parts.get(i));
            if (i != 0) sb.append("/");
        }
        return sb.toString();
    }

    /**
     * creates a file
     * @param name name of file
     * @param content content of file
     * @return the file
     */
    public File createFile(String name, String content) {
        if (children.containsKey(name)) {
            Colors.printError("Name already exists");
            return null;
        }

        File file = new File(name, this, content);
        children.put(name, file);
        return file;
    }


    public void addEntry(Entry entry) {
        if (children.containsKey(entry.getName())) {
            Colors.printError("Name already exists");
            return;
        }
        entry.setParent(this);
        children.put(entry.getName(), entry);
    }

    public Entry getEntry(String name) {
        return children.get(name);
    }

    public void removeEntry(String name) {
        children.remove(name);
    }
}
