import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashSet;

public class ClientThread extends Thread {
    private final static int PORT = 3333;

    private ClientController controller;
    private String ip;
    private HashSet<Question> forbiddenQuestions;
    public ClientThread(ClientController controller, String ip, HashSet<Question> forbiddenQuestions) {
        this.controller = controller;
        this.ip = ip;
        this.forbiddenQuestions = forbiddenQuestions;
    }

    @Override
    public void run() {
        super.run();
        try {
            requestAndSetQuestion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestAndSetQuestion() throws Exception {
        Socket s = new Socket(ip, PORT);
        OutputStream outputStream = s.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        InputStream inputStream = s.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        // send the forbidden questions to the server,
        // so it will generate a new un-seen question.
        objectOutputStream.writeObject(forbiddenQuestions);

        // get a new question from the server
        Question question;
        question = (Question)objectInputStream.readObject();
        controller.setQuestion(question);
        forbiddenQuestions.add(question); 

        objectInputStream.close();
        inputStream.close();
        objectOutputStream.close();
        outputStream.close();
        s.close();
    }
}
