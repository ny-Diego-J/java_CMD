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
            case "clear" -> clearHistory();
            default -> System.out.println("Unknown command");
        }
    }

    public void addHistory(String[] input) {
        StringBuilder command = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            command.append(input[i]);
            command.append(" ");
        }
        if (!command.toString().trim().equals(history.getLast())) {
            this.history.add(command.toString().trim());
        }

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

    private void clearHistory() {
        history.clear();
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
