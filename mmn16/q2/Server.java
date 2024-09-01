import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Server extends Thread {
    public static final String SIGN_CLIENT = "SIGN";
    public static final String RESIGN_CLIENT = "RESIGN";
    private static final int BUFFER_SIZE = 1024;
    public static final int PORT = 6666;
    
    private ArrayList<ClientInfo> clients;
    private DatagramSocket socket;

    public Server() {
        clients = new ArrayList<>();
        socket = null;
    }

    @Override
    public void run() {
        while (true) {
            sendAndReceive();
        }
    }

    public void sendAndReceive() {
        try {
            socket = new DatagramSocket(PORT);

            // receive data from user
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String message = new String(packet.getData()).substring(0, packet.getLength());
            
            // update clients list as necessary
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            if (message.startsWith(SIGN_CLIENT)) {
                clients.add(new ClientInfo(address, port));
            } else if (message.startsWith(RESIGN_CLIENT)) {
                clients.remove(new ClientInfo(address, port));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    public void broadcastMessage(String rawMessage) {
        if (socket == null) {
            return;
        }

        String message = LocalDateTime.now() + ": " + rawMessage;
        clients.forEach(client -> {
            try {
                socket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, client.getAddress(), client.getPort()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}

