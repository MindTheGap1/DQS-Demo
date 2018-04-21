import java.io.BufferedReader;
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
public class Main {
    public static void main(String[] args) {
        System.out.println("--Science Quiz--");
        System.out.println("1: Start Quiz");
        System.out.println("2: Admin Login");
        System.out.print(">");
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();
        switch (input) {
            case 1: System.out.println("Starting Quiz...");
                    getDetails();
                    break;
            case 2: adminLogin();
                    break;
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
                        schoolAccepted = true;
                    }
                } 
            }  
        } catch (FileNotFoundException e) {
            System.out.println("School details missing!");
        }
        if (schoolAccepted == true) {
            Student student = new Student(schoolName, yearGroup);
            startQuiz(student);
        } else {
            System.out.println("Your school name or year group has not been accepted, please try again");
        }
    }

    public static void startQuiz(Student student) {
        Question[] questions = new Question[10];
        Quiz quiz = new Quiz(student, 0, 0, questions);
        //IMPORTANT: questions must be loaded as such, the program will attempt to get 10 and only 10 questions
        //If there are fewer than 10 in the csv file, the remaining slots will be null
        quiz.loadQuestions();
        for (int i = 0; i < quiz.getQuestions().length; i++) {
            try {
                quiz.askQuestion(i);
            } catch (NullPointerException e) {
                //too few questions
            }
            
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
        Scanner in = new Scanner(System.in);
        System.out.println("--ADMIN TOOLS--");
        System.out.println("1: Edit questions");
        System.out.println("2: Edit schools");
        System.out.println("3: Select quiz topic");
        System.out.println("4: View answer statistics");
        System.out.print(">");
        int input = in.nextInt();
        switch (input) {
            case 1: editQuestions();
            case 2: editSchools();
            case 3: selectTopic();
            case 4: viewStats();
        }
    }

    public static void editQuestions() {

    }

    public static void editSchools() {
        System.out.println("1: Edit current schools");
        System.out.println("2: Add new school");
        Scanner choiceIn = new Scanner(System.in);
        int choice = choiceIn.nextInt();
        if (choice == 1) {
            try {
                Scanner schoolReader = new Scanner(new File("schools.csv"));
                
                System.out.println("School Names:");
                int lineCount = 0;
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
            Scanner in = new Scanner(System.in);
            int lineNo = in.nextInt();
            String line = "";
            try {
                line = Files.readAllLines(Paths.get("schools.csv")).get(lineNo);
            } catch (IOException e) {
            }
            System.out.println("Line to edit: " + line);
            System.out.println("Please enter the new school information in the format: SchoolName,year,year,...");
            System.out.print(">");
            Scanner editIn = new Scanner(System.in);
            String replacer = editIn.nextLine();
            replace(replacer,line,"schools.csv");
        } else if (choice == 2) {
        }
    }

    public static void selectTopic() {

    }

    public static void viewStats() {

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
            rdr.close();
            String newText = oldText.replaceAll(oldString, replacer);
            FileWriter writer = new FileWriter(filename);
            writer.write(newText);
            writer.close();
        } catch (IOException e) {

        }
    }
}