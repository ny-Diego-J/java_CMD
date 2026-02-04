package Commands;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PingCommand {
    public static void pingCommand(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: PingCommand <hostname> <port>");
            return;
        }

        int[] ports = {443, 80, 22, 25, 54, 110, 143, 456, 487, 993, 995, 3306, 5432, 25565, 27015, 8080, 8443};
        for (int port : ports) {
            if( pingHost(args[1], port, 3000)){
                System.out.println("Pinging on port " + port);
                break;
            }
        }

    }

    private static boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
