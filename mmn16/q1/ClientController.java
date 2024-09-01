import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ClientController implements Initializable {
    // fixed constants
    private static final int CORRECT_ANSWER_SCORE = 10;
    private static final int WRONG_ANSWER_SCORE = -5;
    private static final int TOTAL_AMOUNT_OF_QUESTIONS = 20;
    // user given constants
    private static String IP; // best option "127.0.0.1"
    private static int QUESTION_DURATION;
    
    private Random random;
    private Timer timer;

    private HashSet<Question> completedQuestions;
    private int score;
    private int remainingTime;
    private int currentQuestionNumber;
    private int currentAnswerNumber;
    private boolean isGameOn;

    // Grid items
    @FXML private Label labelQuestion;
    @FXML private Button buttonAnswer1;
    @FXML private Button buttonAnswer2;
    @FXML private Button buttonAnswer3;
    @FXML private Button buttonAnswer4;
    private Button[] answerButtons; // easier to randomize with a container
    // HBox items (a.k.a. info labels)
    @FXML private Label labelScore;
    @FXML private Label labelQuestionCounter;
    @FXML private Label labelTime;

    public boolean parseArguments(String[] args) {
        if (args.length != 2) {
            System.err.println("Error: Exactly two arguments are needed for this program, one for the server's name, and one for the time to answer each question.");
            return false;
        }

        try {
            IP = args[0];
            QUESTION_DURATION = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format.");
            return false;
        }

        startNewGame();
        return true;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        answerButtons = new Button[4];
        answerButtons[0] = buttonAnswer1;
        answerButtons[1] = buttonAnswer2;
        answerButtons[2] = buttonAnswer3;
        answerButtons[3] = buttonAnswer4;

        random = new Random();
    }

    private void startNewGame() {
        score = 0;
        currentQuestionNumber = 0;
        currentAnswerNumber = 0;
        remainingTime = QUESTION_DURATION;
        isGameOn = true;

        completedQuestions = new HashSet<>();
        requestQuestion();
    }

    private void pressButton(int userAnswer) {
        if (isGameOn) {
            // button press = select answer
            if (timer.isOn()) {
                score += userAnswer == currentAnswerNumber ? CORRECT_ANSWER_SCORE : WRONG_ANSWER_SCORE;
            }
            timer.stopTimer();
            requestQuestion();
        } else {
            // button press = new game
            startNewGame();
        }
    }

    public void requestQuestion() {
        if (currentQuestionNumber >= TOTAL_AMOUNT_OF_QUESTIONS) {
            winGame();
        } else {
            updateInfoLabels();
            new ClientThread(this, IP, completedQuestions).start();
            timer = new Timer(this);
            timer.start();
        }
    }
    
    public void tickTimer() {
        if (remainingTime <= 0) {
            timer.stopTimer();
            score += WRONG_ANSWER_SCORE;
            Platform.runLater(() -> labelScore.setText("Score: " + score));
            return;
        }
        
        Platform.runLater(() -> labelTime.setText("Time: " + --remainingTime));
    }
    
    private void updateInfoLabels() {
        labelScore.setText("Score: " + score);
        labelQuestionCounter.setText("Question " + ++currentQuestionNumber + "/" + TOTAL_AMOUNT_OF_QUESTIONS);
        labelTime.setText("Time: " + (remainingTime = QUESTION_DURATION));
    }
    
    public void setQuestion(Question question) {
        Platform.runLater(() -> {
            String[] answers = question.getAnswers();
            // Create a list of available indices
            List<Integer> availableIndices = IntStream.range(0, answers.length)
                    .boxed()
                    .collect(Collectors.toList());
        
            // Set texts for buttons
            IntStream.range(0, answers.length)
                    .forEach(index -> {
                        Button button = answerButtons[index];
                        int randomIndex = random.nextInt(availableIndices.size());
                        int answerIndex = availableIndices.get(randomIndex);
                        button.setText(question.getAnswers()[answerIndex]);
                        availableIndices.remove(randomIndex);

                        if (answerIndex == question.getCorrectAnswer()) {
                            currentAnswerNumber = index;
                        }
                    });

            labelQuestion.setText(question.getQuestion());
        });
    }

    private void winGame() {
        labelQuestion.setText("Congratulations, you've won the game!");
        buttonAnswer1.setText("New Game");
        buttonAnswer2.setText("New Game");
        buttonAnswer3.setText("New Game");
        buttonAnswer4.setText("New Game");
        isGameOn = false;
    }

    public void clickButtonAnswer1() {
        pressButton(0);
    }

    public void clickButtonAnswer2() {
        pressButton(1);
    }

    public void clickButtonAnswer3() {
        pressButton(2);
    }

    public void clickButtonAnswer4() {
        pressButton(3);
    }
}
