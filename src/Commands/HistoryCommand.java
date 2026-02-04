package Commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HistoryCommand {
    private ArrayList<String> history = new ArrayList<>();

    public void historyMenu(String[] args) {
        if (args.length < 2) {
            System.out.println("Not enough arguments");
            return;
        }
            switch (args[1]) {
                case "list" -> printHistory();
                case "print" -> printHistory();
                default -> System.out.println("Unknown command");
            }
    }

    public void addHistory(String[] history) {
        StringBuilder command = new StringBuilder();
        for (int i = 0; i < history.length; i++) {
            command.append(history[i]);
            command.append(" ");
        }
        this.history.add(command.toString());

    }

    private void printHistory() {
        for (String history : history) {
            System.out.println(history);
        }
    }

    private String getHistory() {
        StringBuilder historyString = new StringBuilder();
        for (String e : history) {
            historyString.append(e);
            historyString.append("\n");
        }
        return historyString.toString();
    }

    public void printHistoryToFile() {
        try {
            FileWriter myWriter = new FileWriter("history.txt");
            myWriter.write(getHistory());
            myWriter.close();  // must close manually
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("history.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }


}
