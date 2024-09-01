import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ServerThread extends Thread {
    private Socket s = null;
    private final ArrayList<Question> questions;
    public ServerThread(Socket socket, ArrayList<Question> questions) {
        s = socket;
        this.questions = questions;
    }

    @Override
    public void run() {
        super.run();
        try {
            handleReadAndWrite();
        } catch (Exception e) {

        }
    }

    @SuppressWarnings("unchecked") 
    private void handleReadAndWrite() throws Exception {
        OutputStream outputStream = s.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        InputStream inputStream = s.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        // get forbidden (solved) questions from the client
        HashSet<Question> forbiddenQuestions = new HashSet<>();
        forbiddenQuestions = (HashSet<Question>)objectInputStream.readObject(); 

        // generate and send a new question
        Question question = getRandomQuestion(forbiddenQuestions);
        objectOutputStream.writeObject(question);

        objectInputStream.close();
        inputStream.close();
        objectOutputStream.close();
        outputStream.close();
        s.close();
    }

    private Question getRandomQuestion(HashSet<Question> forbiddenQuestions) {
        List<Question> availableQuestions = questions.stream()
            .filter(question -> !forbiddenQuestions.contains(question))
            .collect(Collectors.toList());

        Random random = new Random();
        int randomIndex = random.nextInt(availableQuestions.size());

        return availableQuestions.get(randomIndex);
    }
}
