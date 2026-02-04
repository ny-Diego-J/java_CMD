package filesystem;

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

    public Directory createDirectory(String name) {
        if (children.containsKey(name)) {
            System.out.println("Name already exists");
            return null;
        }

        Directory dir = new Directory(name, this);
        children.put(name, dir);
        return dir;
    }

    public Directory createSudoDirectory(String name, String permission) {
        if (children.containsKey(name)) {
            System.out.println("Name already exists");
            return null;
        }
        Directory dir = new Directory(name, this, permission);
        children.put(name, dir);
        return dir;
    }

    public File getFile(String name) {
        Entry e = this.getChildren().get(name);
        File entry = null;
        if (e instanceof File) {
            entry = (File) e;
        }

        if (entry == null) {
            System.out.println("Entry not found");
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
            System.out.println("Entry not found");
            return null;
        } else {
            return entry;
        }
    }


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


    public File createFile(String name, String content) {
        if (children.containsKey(name)) {
            System.out.println("Name already exists");
            return null;
        }

        File file = new File(name, this, content);
        children.put(name, file);
        return file;
    }


    public void addEntry(Entry entry) {
        if (children.containsKey(entry.getName())) {
            throw new IllegalArgumentException("Name already exists: " + entry.getName());
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
