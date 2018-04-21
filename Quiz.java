import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Quiz {
    private Student student;
    private int questionsAnswered;
    private int questionsCorrect;
    private Question[] questions;
    
    public Quiz(Student student, int questionsAnswered, int questionsCorrect, Question[] questions) {
        this.student = student;
        this.questionsAnswered = questionsAnswered;
        this.questionsCorrect = questionsCorrect;
        this.questions = questions;
    }

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }
    public void setQuestionsAnswered(int questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }

    public int getQuestionsCorrect() {
        return questionsCorrect;
    }
    public void setQuestionsCorrect(int questionsCorrect) {
        this.questionsCorrect = questionsCorrect;
    }

    public Question[] getQuestions() {
        return questions;
    }
    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public Question[] loadQuestions() {
        try {
            Scanner questionReader = new Scanner(new File("questionsandanswers.csv"));
            int lineCount = 0;
            while (questionReader.hasNextLine()) {
                String[] details = (questionReader.nextLine()).split(",");
                String questionString = details[0];
                String[] answers = new String[4];
                answers[0] = details[1];
                answers[1] = details[2];
                answers[2] = details[3];
                answers[3] = details[4];
                String[] incorrectAnswers = new String[3];
                String correctAnswer = new String();
                int correctNumber = 0;
                if (details[5].equals("1")) {
                    incorrectAnswers[0] = details[2];
                    incorrectAnswers[1] = details[3];
                    incorrectAnswers[2] = details[4];
                    correctAnswer = details[1];
                    correctNumber = 1;
                } else if (details[5].equals("2")) {
                    incorrectAnswers[0] = details[1];
                    incorrectAnswers[1] = details[3];
                    incorrectAnswers[2] = details[4];
                    correctAnswer = details[2];
                    correctNumber = 2;
                } else if (details[5].equals("3")) {
                    incorrectAnswers[0] = details[1];
                    incorrectAnswers[1] = details[2];
                    incorrectAnswers[2] = details[4];
                    correctAnswer = details[3];
                    correctNumber = 3;
                } else if (details[5].equals("4")) {
                    incorrectAnswers[0] = details[1];
                    incorrectAnswers[1] = details[2];
                    incorrectAnswers[2] = details[3];
                    correctAnswer = details[4];
                    correctNumber = 4;
                }
                Question question = new Question(questionString, answers, incorrectAnswers, correctAnswer, correctNumber);
                questions[lineCount] = question;
                lineCount++;
            }  
        } catch (FileNotFoundException e) {
            System.out.println("School details missing!");
        }

        return questions;
    }

    public void askQuestion(int questionNo) {
        try {
            Question question = questions[questionNo];

            System.out.println(question.getQuestion());
            for (int i = 0; i < 4; i++) {
                System.out.print((i+1) + ": ");
                System.out.println(question.getSingleAnswer(i));
            }
            Scanner in = new Scanner(System.in);
            System.out.print(">");
            int answer = in.nextInt();
            if (answer == question.getCorrectNumber()) {
                System.out.println("Correct");
            } else {
                System.out.println("Incorrect");
            }
        } catch (NullPointerException e) {

        }
    }

}