package Commands;

public class EchoCommand {
    /**
     * Prints out a String
     * @param args whole array that gets printet
     */
    public static void echoOutput(String[] args) {
        StringBuilder output = new StringBuilder();
        for( int i = 1; i < args.length; i++ ) {
            output.append(args[i]).append(" ");
        }
        System.out.println(output.toString());
    }
}
