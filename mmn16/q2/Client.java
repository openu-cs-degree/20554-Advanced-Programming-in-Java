import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/// This Maman doesn't have much comments.
/// I believe in the approch of "good code is code that comments itself".
/// This very specific question (q2) has very short functions, most of them just 1 line,
/// and the functions/variables name indicate exactly what's going on.
///
/// However, I did make some comments in the non-trivial parts for the non-Java readers.
/// Thanks.
public class Client extends Thread {
    private static final int BUFFER_SIZE = 1024;

    private ClientController controller;
    private DatagramSocket socket;

    public Client(ClientController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        socket = null;
        try {
            socket = new DatagramSocket();
            while (true) {
                receiveMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    private void receiveMessage() {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet); // get the message
            controller.setMessage(new String(buffer, 0, packet.getLength())); // view the message
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message, String ipAddress) {
        try {
            socket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, InetAddress.getByName(ipAddress), Server.PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
