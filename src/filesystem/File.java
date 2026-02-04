package filesystem;

import java.time.Instant;

public class File extends Entry {

    private String content = "";

    public File(String name, Directory parent, String content) {
        super(name, parent, FileType.FILE);
        this.content = content;
    }

    public File(String name, Directory parent, String user, String permission, String content) {
        super(name, parent, user, permission, FileType.FILE);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
