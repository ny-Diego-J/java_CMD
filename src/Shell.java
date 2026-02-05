import Commands.*;
import filesystem.Directory;

import java.io.IOException;
import java.util.Scanner;

public class Shell {
    HistoryCommand history = new HistoryCommand();
    private Directory root;
    private Directory currentDir;
    private User currentUser = null;
    private boolean isSudo = false;


    public Shell() throws IOException {
        root = new Directory("/", null);
        currentDir = root = JsonLoader.loadFromJson();
        // PrintDir.initDirs(root, "digi");

    }

    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("- cd <directory>: change current path");
        System.out.println("- mkdir <name>: create new directory");
        System.out.println("- touch <name>: create new file");
        System.out.println("- pwd : show current path");
        System.out.println("- cat <filename>: show content of file");
        System.out.println("- cp <file> <directory>: copy a file to a different directory");
        System.out.println("- echo <text>: prints out the text");
        System.out.println("- edit <filename>: edit the content of a file");
        System.out.println("- login <user> <password>: log in as a User");
        System.out.println("- ls: list files and folders in current directory");
        System.out.println("- mv <file> <directory>: move a file into a different directory");
        System.out.println("- rm <file/directory name>: remove a file or a directory (may require sudo or login)");
        System.out.println("- tree: list folders and subfolders in current directory");
        System.out.println("- save: save current files and directory");
        System.out.println("- load: Load current save");
        System.out.println("- history: command history");
        System.out.println("- chmod <attribute> <entry>: change permissions of a file/folder");


    }

    public void run() throws IOException {
        Scanner sc = new Scanner(System.in);
        history.readFile();
        System.out.println("Enter \"help\" to get a list of commands.");
        currentUser = LoginCommand.autoLogin();
        boolean running = true;

        while (running) {
            PrintDir.printDir(currentDir, currentUser);
            String input = sc.nextLine().trim();
            if (input.isEmpty())
                continue;

            String[] args = input.split("\\s+");

            isSudo = false;
            if (args[0].equals("sudo")) {
                isSudo = true;
                if (!canSudo())
                    continue;
                args = java.util.Arrays.copyOfRange(args, 1, args.length);
                if (args.length == 0) {
                    System.out.println("Usage: sudo <command>");
                    continue;
                }
            }
            history.addHistory(args);
            history.printHistoryToFile();
            running = handleCommand(args);
        }
    }

    private boolean canSudo() {
        if (currentUser == null) {
            System.out.println("Not logged in");
            return false;
        }
        if (!currentUser.doesSudo) {
            System.out.println("Permission denied (sudo required)");
            return false;
        }
        return true;
    }

    private boolean handleCommand(String[] args) {
        LsCommand lsCommand = new LsCommand();
        try {
            switch (args[0]) {
                case "pwd" -> PwdCommand.printPath(currentDir);
                case "ls" -> lsCommand.listDirectory(currentDir, args);
                case "cd" -> currentDir = CdCommand.changeDirectory(currentDir, args[1]);
                case "mkdir" -> MkdirCommand.makeDir(currentDir, args);

                case "touch" -> TouchCommand.createFile(currentDir, args);
                case "cat" -> CatCommand.getContent(currentDir, args);
                case "edit" -> EditCommand.editFile(currentDir, args);

                case "cp" -> CpCommand.copyFile(currentDir, args);
                case "mv" -> MvCommand.moveFile(currentDir, args);

                case "rm" -> RmCommand.remove(currentDir, args, currentUser);

                case "echo" -> EchoCommand.echoOutput(args);

                case "tree" -> TreeCommand.tree(currentDir);

                case "chmod" -> ChmodCommand.changePermission(currentUser, currentDir, args);

                case "login" -> currentUser = LoginCommand.login(args, isSudo);
                case "whoami" -> System.out.println(currentUser == null ? "guest" : currentUser.name);
                case "logout" -> currentUser = null;

                case "save" -> SaveToJson.saveToJson(root);
                case "load" -> JsonLoader.loadFromJson();

                case "history" -> history.historyMenu(args);

                case "ping" -> PingCommand.pingCommand(args);

                case "exit" -> {
                    return false;
                }
                case "help" -> printHelp();
                default -> System.out.println("Invalid command");
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;
        }
    }
}
