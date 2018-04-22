import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.regex.*;

public class Main {
    public static String topic = "spacequestions.csv";

    public static void main(String[] args) {
        while (true) {
            System.out.println("--Science Quiz--");
            System.out.println("1: Start Quiz");
            System.out.println("2: Admin Login");
            System.out.println("0: Exit");
            int input = Integer.parseInt(checkInput("[012]"));
            switch (input) {
                case 1: System.out.println("Starting Quiz...");
                        getDetails();
                        break;
                case 2: adminLogin();
                        break;
                case 0: System.exit(0);
            }
        }
    }

    public static void getDetails() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your school: ");
        String schoolName = in.nextLine();
        System.out.println("Enter your school year group: ");
        //yearGroup is String and not int to allow for easy searching in csv file
        String yearGroup = in.nextLine();
        boolean schoolAccepted = false;

        try {
            Scanner schoolReader = new Scanner(new File("schools.csv"));
            while (schoolReader.hasNextLine()) {
                String[] details = (schoolReader.nextLine()).split(",");
                for (int i = 1; i < details.length; i++) {
                    if (schoolName.equals(details[0])) {
                        System.out.println("Found");
                        schoolAccepted = true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("School details missing!");
        }
        if (schoolAccepted == true) {
            Student student = new Student(schoolName, yearGroup);
            startQuiz(student, topic);
        } else {
            System.out.println("Your school name or year group has not been accepted, please try again");
        }
    }

    public static void startQuiz(Student student, String topic) {
        Question[] questions = new Question[10];
        Quiz quiz = new Quiz(student.getSchool(), student.getYearGroup(), 0, 0, questions, false);
        quiz.setTopic(topic);
        //IMPORTANT: questions must be loaded as such, the program will attempt to get 10 and only 10 questions
        //If there are fewer than 10 in the csv file, the remaining slots will be null
        quiz.loadQuestions();
<<<<<<< HEAD

=======
        System.out.println("Starting quiz...");
>>>>>>> 6a9613da540ff8706401999fd2f291ea263ec607
        for (int i = 0; i < quiz.getQuestions().length; i++) {
            try {
                if (quiz.getQuizQuit() == true) {
                    break;
                } else if (quiz.getQuizRestart() == true) {
                    i = 0;
                    //quiz.setQuizRestart(false);
                    startQuiz(student, topic);;
                } else {
                    quiz.askQuestion(i);        
                }
            } catch (NullPointerException e) {
                //too few questions
            }
<<<<<<< HEAD
        }
=======
        }    
>>>>>>> 6a9613da540ff8706401999fd2f291ea263ec607
        quiz.saveResults();
        System.out.println("That's the end of the quiz!");
        System.out.println("Do you want to see your results? [Y/N]");
        Scanner resultIn = new Scanner(System.in);
        String seeResult = resultIn.nextLine();
        if (seeResult.toLowerCase().equals("y")) {
            System.out.println("You scored " + quiz.getQuestionsCorrect() + " out of " + quiz.getQuestionsAnswered());
        }
    }

    public static void adminLogin() {
        Scanner in = new Scanner(System.in);
        System.out.println("Username: ");
        String adminUsername = in.next();
        System.out.println("Password: ");
        String adminPassword = in.next();

        //check if password is correct
        boolean loginSuccess = false;
        try {
            Scanner adminReader = new Scanner(new File("admindetails.csv"));

            while (adminReader.hasNextLine()) {
                String[] details = (adminReader.nextLine()).split(",");
                if (adminUsername.equals(details[0]) && adminPassword.equals(details[1])) {
                    System.out.println("Login Success");
                    loginSuccess = true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Login details missing!");
        }

        if (loginSuccess == true) {
            adminPage();
        } else {
            System.out.println("Login failed (NOTE: usernames and passwords are case sensitive)");
        }
    }

    public static void adminPage() {
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("--ADMIN TOOLS--");
            System.out.println("1: Edit questions");
            System.out.println("2: Edit schools");
            System.out.println("3: Select quiz topic");
            System.out.println("4: View answer statistics");
            System.out.println("0: Logout");
            int input = Integer.parseInt(checkInput("[01234]"));
            switch (input) {
                case 1: editQuestions();
                        break;
                case 2: editSchools();
                        break;
                case 3: selectTopic();
                        break;
                case 4: viewStats();
                        break;
                case 0:
                System.out.println("Are you sure? [Y/N]");
                Scanner logIn = new Scanner(System.in);
                String logChoice = logIn.nextLine().toLowerCase();
                if (logChoice.equals("y")) {
                    return;
                }
                break;
                default: System.out.println("Please enter a valid input");
            }
        }
    }

    public static void editQuestions() {
        System.out.println("1: Edit current questions");
        System.out.println("2: Add new question");
        System.out.println("0: Exit");
        int choice = Integer.parseInt(checkInput("[012]"));
        if (choice == 1) {
            System.out.println("Edit which question file? [Remember to add the .csv file extension]");
            Scanner fileIn = new Scanner(System.in);
            String filename = fileIn.nextLine();
            try {
                Scanner questionReader = new Scanner(new File(filename));
                System.out.println("Questions:");
                int lineCount = 1;
                while (questionReader.hasNextLine()) {
                    String[] details = (questionReader.nextLine()).split(",");
                    System.out.print(lineCount + ": ");
                    System.out.println(details[0]+","+details[1]+","+details[2]+","+details[3]+","+details[4]+","+details[5]);
                    lineCount++;
                }
            } catch (IOException e) {
            }
            System.out.println("Choose a number to edit a question");
            Scanner in = new Scanner(System.in);
            int lineNo = Integer.parseInt(checkInput("[123456789]|[1][0]"));;
            String line = "";
            try {
                line = Files.readAllLines(Paths.get(filename)).get(lineNo-1);
            } catch (IOException e) {
            }
            System.out.println("Line to edit: " + line);
            System.out.println("Please enter the new question information in the format: question,option,option,option,option,correctoptionnumber");
            System.out.print(">");
            Scanner editIn = new Scanner(System.in);
            String replacer = editIn.nextLine();
            System.out.println("Writing " + replacer + " to " + line + filename);
            replaceQuestion(replacer, filename, lineNo);
            //replace(replacer,line,filename);
        } else if (choice == 2) {
            System.out.println("Edit which question file? [Remember to add the .csv file extension]");
            Scanner fileIn = new Scanner(System.in);
            String filename = fileIn.nextLine();
            System.out.println("Please enter the new question information in the format: question,option,option,option,option,correctoptionnumber");
            System.out.print(">");
            Scanner editIn = new Scanner(System.in);
            String newQuestion = editIn.nextLine();
            try {
                BufferedWriter bufOut = new BufferedWriter(new FileWriter(filename, true));
                bufOut.write(newQuestion + "\n");
                bufOut.close();
            } catch (IOException e) {
            }
        }
    }

    public static void editSchools() {
        System.out.println("1: Edit current schools");
        System.out.println("2: Add new school");
        System.out.println("0: Exit");
        int choice = Integer.parseInt(checkInput("[012]"));
        if (choice == 1) {
            try {
                Scanner schoolReader = new Scanner(new File("schools.csv"));

                System.out.println("School Names:");
                int lineCount = 1;
                while (schoolReader.hasNextLine()) {
                    String[] details = (schoolReader.nextLine()).split(",");
                    System.out.print(lineCount + ": ");
                    System.out.println(details[0]);
                    lineCount++;
                }
            } catch (FileNotFoundException e) {
                System.out.println("School details missing!");
            }
            System.out.println("Choose a number to edit a school");

            // Add Another Check as Could pick a line that doesn't exist
            int lineNo = Integer.parseInt(checkInput("[\\d]+"));

            String line = "";
            try {
                line = Files.readAllLines(Paths.get("schools.csv")).get(lineNo-1);
            } catch (IOException e) {
            }
            System.out.println("Line to edit: " + line);
            System.out.println("Please enter the new school information in the format: SchoolName,year,year,...");
            System.out.print(">");
            Scanner editIn = new Scanner(System.in);
            String replacer = editIn.nextLine();
            replace(replacer,line,"schools.csv");
        } else if (choice == 2) {
            System.out.println("Please enter the new school information in the format: SchoolName,year,year,...");
            System.out.print(">");
            Scanner editIn = new Scanner(System.in);
            String newSchool = editIn.nextLine();
            try {
                BufferedWriter bufOut = new BufferedWriter(new FileWriter("schools.csv", true));
                bufOut.write(newSchool + "\n");
                bufOut.close();
            } catch (IOException e) {
            }
        }
    }

    public static void selectTopic() {
        System.out.println("Enter the name of the topic you wish to select: ");
        Scanner topicIn = new Scanner(System.in);
        topic = topicIn.nextLine();
    }

    public static void viewStats() {
        try {
            System.out.println("1: View all results");
            System.out.println("2: View results for specific school");
            System.out.println("3: View results for specific year group");
            System.out.println("4: Detailed summary");
            int option = Integer.parseInt(checkInput("[1234]"));

            if (option == 1) {
                viewAllStats();
            } else if (option == 2) {
                viewSchoolStats();
            } else if (option == 3) {
                viewYearStats();
            } else if (option == 4) {
                viewSummaryStats();
            }
        } catch (InputMismatchException e) {

        }
    }

    public static void viewAllStats() {
        try {
            Scanner statReader = new Scanner(new File("studentresults.csv"));
            while (statReader.hasNextLine()) {
                String[] details = (statReader.nextLine()).split(",");
                System.out.print("Student from: ");
                System.out.println(details[0]);
                System.out.print("Year Group: ");
                System.out.println(details[1]);
                System.out.print("Topic: ");
                System.out.println(details[4]);
                System.out.print("Questions Correct: ");
                System.out.print(details[3]);
                System.out.print(" out of ");
                System.out.println(details[2]);
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found!");
        }
    }

    public static void viewSchoolStats() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the school you wish to view: ");
        String schoolName = in.nextLine();
        try {
            Scanner statReader = new Scanner(new File("studentresults.csv"));
            while (statReader.hasNextLine()) {
                String[] details = (statReader.nextLine()).split(",");
                if (details[0].contains(schoolName)) {
                    System.out.print("Student from: ");
                    System.out.println(details[0]);
                    System.out.print("Year Group: ");
                    System.out.println(details[1]);
                    System.out.print("Topic: ");
                    System.out.println(details[4]);
                    System.out.print("Questions Correct: ");
                    System.out.print(details[3]);
                    System.out.print(" out of ");
                    System.out.println(details[2]);
                    System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found!");
        }
    }

    public static void viewYearStats() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the year you wish to view: ");
        String yearGroup = in.nextLine();
        try {
            Scanner statReader = new Scanner(new File("studentresults.csv"));
            while (statReader.hasNextLine()) {
                String[] details = (statReader.nextLine()).split(",");
                if (details[1].equals(yearGroup)) {
                    System.out.print("Student from: ");
                    System.out.println(details[0]);
                    System.out.print("Year Group: ");
                    System.out.println(details[1]);
                    System.out.print("Topic: ");
                    System.out.println(details[4]);
                    System.out.print("Questions Correct: ");
                    System.out.print(details[3]);
                    System.out.print(" out of ");
                    System.out.println(details[2]);
                    System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found!");
        }
    }

    public static void viewSummaryStats() {
        try {
            Scanner statReader = new Scanner(new File("studentresults.csv"));
            int highestScore = 0;
            int sum = 0;
            int lineCount = 0;
            while (statReader.hasNextLine()) {
                String[] details = (statReader.nextLine()).split(",");
                if (Integer.parseInt(details[3]) > highestScore) {
                    highestScore = Integer.parseInt(details[3]);
                }
                sum += Integer.parseInt(details[3]);
                lineCount++;
            }
            float avg = sum / lineCount;
            System.out.println("The highest raw score was " + highestScore);
            System.out.println("The average raw score was " + avg);
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found!");
        }
    }

    public static void replace(String replacer, String oldString, String filename) {
        try {
            File file = new File(filename);
            BufferedReader rdr = new BufferedReader(new FileReader(file));
            String line = "";
            String oldText = "";

            while ((line = rdr.readLine()) != null) {
                oldText += line + "\r\n";
            }
            System.out.println(oldText);
            rdr.close();
            String newText = oldText.replaceAll(oldString, replacer);
            FileWriter writer = new FileWriter(filename);
            System.out.println("Writing " + newText);
            writer.write(newText);
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void replaceQuestion(String replacer, String filename, int lineNo) {
        try {
            Scanner questionlineReader = new Scanner(new File(filename));
            int lineCount = 0;
            FileWriter writer = new FileWriter("temp.csv");
            while (questionlineReader.hasNextLine()) {
                String details = questionlineReader.nextLine();
                if (lineCount == lineNo) {
                    writer.write(replacer);
                    writer.write("\n");
                } else {
                    writer.write(details);
                    writer.write("\n");
                }
                lineCount++;
            }
            writer.close();
            FileWriter copywriter = new FileWriter(filename);
            Scanner tempReader = new Scanner(new File("temp.csv"));
            while (tempReader.hasNextLine()) {
                String details = tempReader.nextLine();
                copywriter.write(details);
                copywriter.write("\n");
            }
            copywriter.close();
        } catch (IOException e) {
        }
    }
<<<<<<< HEAD

    public static String checkInput(String pattern) {
        Scanner in = new Scanner(System.in);
        String userInput = "";
        System.out.print("> ");
        userInput = in.nextLine();
        while (!regexChecker(userInput,pattern)) {
            System.out.print("Invalid Input > ");
            userInput = in.nextLine();
        }
        return userInput;
    }

    public static Boolean regexChecker(String userInput, String regexPattern) {
        return Pattern.matches(regexPattern,userInput.trim());
    }

=======
>>>>>>> 6a9613da540ff8706401999fd2f291ea263ec607
}
