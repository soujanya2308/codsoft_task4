import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    private String questionText;
    private ArrayList<String> options;
    private int correctOptionIndex;

    public Question(String questionText, ArrayList<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }
}

class Quiz {
    private ArrayList<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;

    public Quiz(ArrayList<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.timer = new Timer();
    }

    public void startQuiz() {
        displayQuestion();
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);

            System.out.println("Question: " + currentQuestion.getQuestionText());
            ArrayList<String> options = currentQuestion.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }

            startTimer();
            getUserAnswer();
        } else {
            endQuiz();
        }
    }

    private void getUserAnswer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your answer (1-" + questions.get(currentQuestionIndex).getOptions().size() + "): ");
        int userAnswer = scanner.nextInt();

        checkAnswer(userAnswer - 1); // Adjusting for 0-based index
    }

    private void checkAnswer(int userAnswer) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        if (userAnswer == currentQuestion.getCorrectOptionIndex()) {
            System.out.println("Correct!\n");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer was: " +
                    currentQuestion.getOptions().get(currentQuestion.getCorrectOptionIndex()) + "\n");
        }

        stopTimer();
        currentQuestionIndex++;
        displayQuestion();
    }

    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time's up! Moving to the next question.");
                stopTimer();
                currentQuestionIndex++;
                displayQuestion();
            }
        }, 15000); // 15 seconds per question
    }

    private void stopTimer() {
        timer.cancel();
        timer = new Timer();
    }

    private void endQuiz() {
        System.out.println("Quiz completed!");
        System.out.println("Your final score: " + score + "/" + questions.size());

        // Display summary of correct/incorrect answers if needed
    }
}

public class QuizApp {
    public static void main(String[] args) {
        // Create questions for the quiz
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?",
                new ArrayList<>(List.of("Berlin", "Madrid", "Paris", "Rome")), 2));
        questions.add(new Question("Which planet is known as the Red Planet?",
                new ArrayList<>(List.of("Venus", "Mars", "Jupiter", "Saturn")), 1));
        // Add more questions as needed

        // Create and start the quiz
        Quiz quiz = new Quiz(questions);
        quiz.startQuiz();
    }
}