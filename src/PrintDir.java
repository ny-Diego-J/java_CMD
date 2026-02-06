import Commands.*;
import filesystem.Directory;

public class PrintDir {
    /**
     * prints out the directory to display bevore
     * @param dir dir to display
     * @param user user to get
     */
    public static void printDir(Directory dir, User user) { //TODO: Fix hardcodet "~"
        if (user != null) {
            System.out.print(user.name + "$ ");
        }
        String rawString = dir.getPath();
        assert user != null;

        rawString = rawString.replace("/home/digi", "~");
        System.out.print(rawString + " ❯ ");
    }

    /**
     * backup to get standard directories
     * @param root root directory
     * @param username name of normal user
     */
    public static void initDirs(Directory root, String username) {

        Directory bin = root.createSudoDirectory("bin", "drwxr-xr-x");
        Directory boot = root.createSudoDirectory("boot", "drwx------");
        Directory dev = root.createSudoDirectory("dev", "drwxr-xr-x");
        Directory etc = root.createSudoDirectory("etc", "drwxr-xr-x");
        Directory home = root.createSudoDirectory("home", "drwxr-xr-x");
        Directory lib = root.createSudoDirectory("lib", "drwxr-xr-x");
        Directory lib64 = root.createSudoDirectory("lib64", "drwxr-xr-x");
        Directory mnt = root.createSudoDirectory("mnt", "drwxr-xr-x");
        Directory opt = root.createSudoDirectory("opt", "drwxr-xr-x");
        Directory proc = root.createSudoDirectory("proc", "dr-xr-xr-x");
        Directory rootUser = root.createSudoDirectory("root", "drwx------");
        Directory run = root.createSudoDirectory("run", "drwxr-xr-x");
        Directory sbin = root.createSudoDirectory("sbin", "drwxr-xr-x");
        Directory srv = root.createSudoDirectory("srv", "drwxr-xr-x");
        Directory sys = root.createSudoDirectory("sys", "dr-xr-xr-x");
        Directory tmp = root.createSudoDirectory("tmp", "drwxrwxrwt");
        Directory usr = root.createSudoDirectory("usr", "drwxr-xr-x");
        Directory var = root.createSudoDirectory("var", "drwxr-xr-x");

        // /usr
        usr.createSudoDirectory("bin", "drwxr-xr-x");
        usr.createSudoDirectory("lib", "drwxr-xr-x");
        usr.createSudoDirectory("share", "drwxr-xr-x");
        usr.createSudoDirectory("local", "drwxr-xr-x");
        usr.createSudoDirectory("include", "drwxr-xr-x");
        usr.createSudoDirectory("sbin", "drwxr-xr-x");

        // /var
        Directory varLog = var.createSudoDirectory("log", "drwxr-xr-x");
        var.createSudoDirectory("tmp", "drwxrwxrwt");
        var.createSudoDirectory("cache", "drwxr-xr-x");
        var.createSudoDirectory("lib", "drwxr-xr-x");

        // /etc
        etc.createSudoDirectory("pacman.d", "drwxr-xr-x");
        etc.createSudoDirectory("systemd", "drwxr-xr-x");
        etc.createSudoDirectory("ssh", "drwxr-xr-x");
        etc.createSudoDirectory("X11", "drwxr-xr-x");

        // /home/user
        Directory userHome = home.createSudoDirectory(username, "drwx------");

        userHome.createDirectory("Desktop");
        userHome.createDirectory("Documents");
        userHome.createDirectory("Downloads");
        userHome.createDirectory("Music");
        userHome.createDirectory("Pictures");
        userHome.createDirectory("Videos");
        userHome.createDirectory("Templates");
        userHome.createDirectory("Public");
        userHome.createFile("test.txt", "Hallo");

        varLog.createFile("syslog", "");
    }


}
