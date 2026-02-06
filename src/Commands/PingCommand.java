package Commands;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PingCommand {

    /**
     * Pings an internet address
     * @param args args[1] is the address
     */
    public static void pingCommand(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: PingCommand <hostname> <port>");
            return;
        }

        int[] ports = {443, 80, 22, 25, 54, 110, 143, 456, 487, 993, 995, 3306, 5432, 25565, 27015, 8080, 8443};
        for (int port : ports) {
            if (pingHost(args[1], port, 2000)) {
                System.out.println("Pinging on port " + port);
                return;
            }
        }
        System.out.println("Could not find hostname or port");

    }

    /**
     * Connects to an address
     * @param host Address to connect to
     * @param port Port to connect with
     * @param timeout time to try to connect
     * @return if connection worked
     */
    private static boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            socket.getOutputStream().write("Ping".getBytes());
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
