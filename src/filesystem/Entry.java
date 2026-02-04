package filesystem;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class Entry {

    protected String name;
    protected Directory parent;
    protected String user;
    protected String permission;
    protected Instant lastModified;
    protected FileType fileType;

    public Entry(String name, Directory parent, FileType fileType) {
        this.name = name;
        this.parent = parent;
        this.user = "root";
        this.fileType = fileType;
        this.lastModified = Instant.now();
        StringBuilder sc = new StringBuilder();
        if (fileType == FileType.DIRECTORY) {
            sc.append("drwxr-xr-x");
        } else {
            sc.append(".rw-r--r--");
        }

        this.permission = sc.toString();
    }
    public Entry(String name, Directory parent, FileType fileType, String permission) {
        this.name = name;
        this.parent = parent;
        this.user = "root";
        this.fileType = fileType;
        this.lastModified = Instant.now();
        this.permission = permission;
    }


    public Entry(String name, Directory parent, String user, String permission, FileType fileType) {
        this.name = name;
        this.parent = parent;
        this.user = user;
        this.permission = permission;
        this.lastModified = Instant.now();
        this.fileType = fileType;
    }

    protected void touch() {
        this.lastModified = Instant.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        touch();
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
        touch();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        touch();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
        touch();
    }

    public String getFormattedLastModified() {
        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        return lastModified
                .atZone(ZoneId.systemDefault())
                .format(fmt);
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
