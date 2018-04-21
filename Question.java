public class Question {
    private String question;
    private String[] answers;
    private String[] incorrectAnswers;
    private String correctAnswer;
    private int correctNumber;

    public Question(String question, String[] answers, String[] incorrectAnswers, String correctAnswer, int correctNumber) {
        this.question = question;
        this.answers = answers;
        this.incorrectAnswers = incorrectAnswers;
        this.correctAnswer = correctAnswer;
        this.correctNumber = correctNumber;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String[] getIncorrectAnswers() {
        return incorrectAnswers;
    }
    public void setIncorrectAnswers(String[] incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getCorrectNumber() {
        return correctNumber;
    }
    public void setCorrectNumber(int correctNumber) {
        this.correctNumber = correctNumber;
    }

    public String getSingleAnswer(int answerNo) {
        return answers[answerNo];
    }
}