import java.io.Serializable;
import java.util.Objects;

public class Question implements Serializable {
    private static final long serialVersionUID = 69L;
    private static final int NUM_OF_ANSWERS = 4;
    
    private final String question;
    private final String[] answers;
    private final int answer;

    public Question(String question, String answer1, String answer2, String answer3, String answer4, int correctAnswer) {
        this.question = question;
        this.answers = new String[NUM_OF_ANSWERS];
        answers[0] = answer1;
        answers[1] = answer2;
        answers[2] = answer3;
        answers[3] = answer4;
        answer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return answer;
    }

    public boolean isAnswerCorrect(int answer) {
        return this.answer == answer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(question);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Question && ((Question)obj).question.equals(question);
    }
}
