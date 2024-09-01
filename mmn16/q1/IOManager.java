import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that holds the IO logic of parsing questions
 */
public class IOManager {
    public static final String questionsPath = "src\\questions.txt";

    private static final int LINES_PER_QUESTION = 5;

    private static Scanner input;
    private static int lineCount = 0; // the number of lines in the scanned file

    /**
     * @return a scanner whose source is the file. Might fail if file is corrupted or does not exist.
     */
    private static boolean getScannerOfQuestionsFile() {
        try {
            input = new Scanner(new File(questionsPath));
            lineCount = (int)Files.lines(Path.of(questionsPath)).count();
            if (lineCount % LINES_PER_QUESTION != 0) {
                System.out.println("Error: Questions file is corrupted!\nEach question should be represented by 5 and only 5 lines.\nTerminating program.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error: Questions file does not exist!\nTerminating program.");
            return false;
        }

        return true;
    }

    /**
     * @param questions a ArrayList to add a question to
     * @param input a Scanner to read the Question's data from
     * @return whether the addition succeeded
     */
    private static boolean parseQuestion(ArrayList<Question> questions, Scanner input) {
        String question = input.nextLine();
        String answer1 = input.nextLine();
        String answer2 = input.nextLine();
        String answer3 = input.nextLine();
        String answer4 = input.nextLine();

        return questions.add(new Question(
            question,
            answer1,
            answer2,
            answer3,
            answer4,
            0
        ));
    }

    /**
     * Parse a questions file.
     * @return whether the parsing succeeded
     */
    public static boolean parseQuestions(ArrayList<Question> questions) {
        if (!getScannerOfQuestionsFile()) {
            return false;
        }

        int questionCount = 0;
        int totalQuestionsFound = lineCount / LINES_PER_QUESTION;
        while (questionCount++ < totalQuestionsFound && parseQuestion(questions, input));
        input.close();

        return questions.size() == totalQuestionsFound;
    }
}
