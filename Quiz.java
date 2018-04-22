import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Quiz extends Student{
    private Student student;
    private String topic;
    private int questionsAnswered;
    private int questionsCorrect;
    private Question[] questions;
    private boolean quizQuit;
    private boolean quizRestart = false;
    
    public Quiz(String school, String yearGroup, int questionsAnswered, int questionsCorrect, Question[] questions, boolean quizQuit) {
        super(school, yearGroup);
        this.questionsAnswered = questionsAnswered;
        this.questionsCorrect = questionsCorrect;
        this.questions = questions;
        this.quizQuit = quizQuit;
    }

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
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

    public boolean getQuizQuit() {
        return quizQuit;
    }
    public void setQuizQuit(boolean quizQuit) {
        this.quizQuit = quizQuit;
    }

    public boolean getQuizRestart() {
        return quizRestart;
    }
    public void setQuizRestart(boolean quizRestart) {
        this.quizRestart = quizRestart;
    }

    public Question[] loadQuestions() {
        try {
            Scanner questionReader = new Scanner(new File(topic));
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
            System.out.println("0: Quit");

            //int answer = -1;
            int answer = Integer.parseInt(Main.checkInput("[01234]"));

            if (answer == question.getCorrectNumber()) {
                System.out.println("Correct");
                this.questionsAnswered += 1;
                this.questionsCorrect += 1;
            } else if (answer == 0) {
                System.out.println("1: Quit");
                System.out.println("2: Restart");
                Scanner qrIn = new Scanner(System.in);
                int qr = qrIn.nextInt();
                if (qr == 1) {
                    System.out.println("Are you sure [Y/N]?");
                    Scanner quitIn = new Scanner(System.in);
                    String quit = quitIn.nextLine();
                    if (quit.toLowerCase().equals("y")) {
                        quitQuiz();
                    } else {
                        askQuestion(questionNo);
                    }    
                } else if (qr == 2) {
                    System.out.println("Are you sure [Y/N]?");
                    Scanner restartIn = new Scanner(System.in);
                    String restart = restartIn.nextLine();
                    if (restart.toLowerCase().equals("y")) {
                        restartQuiz();
                    } else {
                        askQuestion(questionNo);
                    }  
                }
                
            } else {
                System.out.println("Incorrect");
                this.questionsAnswered += 1;
            }
        } catch (NullPointerException e) {

        }
    }

    public void quitQuiz() {
        System.out.println("You quit the quiz early");
        saveResults();
        this.setQuizQuit(true);

    }


        
    
    public void restartQuiz() {
        System.out.println("You have decided to restart the quiz");
        this.setQuizRestart(true);
        this.setQuestionsAnswered(0);
        this.setQuestionsCorrect(0);
    }
    
    public void saveResults() {
        try {
            FileWriter writer = new FileWriter("studentresults.csv", true);
            writer.append(this.getSchool() + "," + this.getYearGroup() + "," + this.getQuestionsAnswered() + "," + this.getQuestionsCorrect() + "," + this.getTopic());
            writer.append("\n");
            writer.close();
        } catch (IOException e) {
        }
    }

}