import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final static int PORT = 3333;
    private ArrayList<Question> questions;

    public Server() {
        System.out.println("Starting server at port " + PORT + "!");
        if (!parseQuestions()) {
            return;
        }

        ServerSocket sc = null;
        Socket s = null;
        try {
            sc = new ServerSocket(PORT);
            while (true) {
                s = sc.accept();
                new ServerThread(s, questions).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (sc != null) {
                    sc.close();
                    System.out.println("Server shut down properly!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean parseQuestions() {
        questions = new ArrayList<>();
        return IOManager.parseQuestions(questions);
    }

    public static void main(String[] args) {
        new Server();
    }
}
