import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class Exam {
    private String title;
    private int duration; // in minutes
    private List<Question> questions;

    public Exam(String title, int duration) {
        this.title = title;
        this.duration = duration;
        this.questions = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
}

class Question {
    private String content;
    private List<String> options;
    private int correctOption;

    public Question(String content) {
        this.content = content;
        this.options = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }
}

class User {
    private String username;
    private String password;
    private String email;
    private String fullName;

    public User(String username, String password, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

public class OnlineExamination {
    private List<Exam> exams;
    private List<User> users;
    private ExamCreator examCreator;
    private Timer examTimer;

    public OnlineExamination() {
        exams = new ArrayList<>();
        users = new ArrayList<>();
        examCreator = new ExamCreator(this);
        examTimer = new Timer();
    }

    public void createExam() {
        // Create exam logic
    }

    public void addExam(Exam exam) {
        exams.add(exam);
    }

    public void registerUser(String username, String password, String email, String fullName) {
        // Check if the username is already taken
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username " + username + " is already taken.");
                return;
            }
        }

        // Create a new user and add to the list
        User newUser = new User(username, password, email, fullName);
        users.add(newUser);
        System.out.println("User " + username + " registered successfully.");
    }

    public boolean authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void takeExam(User user, Exam exam) {
        // Start the exam timer
        examTimer.schedule(new ExamTimerTask(user, exam), exam.getDuration() * 60 * 1000); // Convert minutes to milliseconds
    }

    private class ExamTimerTask extends TimerTask {
        private User user;
        private Exam exam;

        public ExamTimerTask(User user, Exam exam) {
            this.user = user;
            this.exam = exam;
        }

        @Override
        public void run() {
            // Auto-submit the exam when the time expires
            submitExam(user, exam);
        }
    }

    private void submitExam(User user, Exam exam) {
        // Logic to submit the exam with recorded student responses
        System.out.println("Time's up! Exam auto-submitted for user: " + user.getUsername());
        // You can add your logic here to handle the submission process, like storing responses or calculating scores.
    }

    public static void main(String[] args) {
        OnlineExamination system = new OnlineExamination();

        // Register sample users
        system.registerUser("john_doe", "password1", "john.doe@example.com", "John Doe");
        system.registerUser("jane_smith", "password2", "jane.smith@example.com", "Jane Smith");

        // Test authentication and exam taking
        if (system.authenticateUser("john_doe", "password1")) {
            System.out.println("Authentication successful for John Doe.");
        } else {
            System.out.println("Authentication failed for John Doe.");
        }

        if (system.authenticateUser("jane_smith", "password2")) {
            System.out.println("Authentication successful for Jane Smith.");
        } else {
            System.out.println("Authentication failed for Jane Smith.");
        }
    }
}