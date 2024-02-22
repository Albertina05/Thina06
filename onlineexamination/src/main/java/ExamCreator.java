import java.util.Scanner;

public class ExamCreator {
    private OnlineExamination system;
    private Scanner scanner;

    public ExamCreator(OnlineExamination system) {
        this.system = system;
        this.scanner = new Scanner(System.in);
    }

    public void createExam() {
        System.out.println("Enter the title of the exam:");
        String title = scanner.nextLine();

        System.out.println("Enter the duration of the exam (in minutes):");
        int duration = Integer.parseInt(scanner.nextLine());

        Exam exam = new Exam(title, duration);

        // Add questions to the exam
        while (true) {
            System.out.println("Enter the type of question (MCQ/TF/SA) or 'done' to finish:");
            String type = scanner.nextLine().toUpperCase();
            if (type.equals("DONE")) {
                break;
            }

            System.out.println("Enter the content of the question:");
            String content = scanner.nextLine();

            switch (type) {
                case "MCQ":
                    createMCQ(content, exam);
                    break;
                case "TF":
                    createTF(content, exam);
                    break;
                case "SA":
                    createSA(content, exam);
                    break;
                default:
                    System.out.println("Invalid question type.");
                    break;
            }
        }

        // Add the created exam to the system
        system.addExam(exam);

        System.out.println("Exam created successfully!");
    }

    private void createMCQ(String content, Exam exam) {
        Question question = new Question(content);

        System.out.println("Enter the options for the multiple-choice question (type 'done' to finish adding options):");
        while (true) {
            String option = scanner.nextLine();
            if (option.equalsIgnoreCase("done")) {
                break;
            }
            question.getOptions().add(option);
        }

        System.out.println("Enter the index of the correct option:");
        int correctOption = Integer.parseInt(scanner.nextLine());
        question.setCorrectOption(correctOption);

        exam.addQuestion(question);
    }

    private void createTF(String content, Exam exam) {
        Question question = new Question(content);

        System.out.println("Enter the correct answer (True/False):");
        String correctAnswer = scanner.nextLine().toLowerCase();

        if (correctAnswer.equals("true")) {
            question.setCorrectOption(1);
            question.getOptions().add("True");
            question.getOptions().add("False");
        } else if (correctAnswer.equals("false")) {
            question.setCorrectOption(2);
            question.getOptions().add("False");
            question.getOptions().add("True");
        } else {
            System.out.println("Invalid input for True/False question.");
            return;
        }

        exam.addQuestion(question);
    }

    private void createSA(String content, Exam exam) {
        Question question = new Question(content);

        System.out.println("Enter the correct answer for the short answer question:");
        String correctAnswer = scanner.nextLine();
        question.getOptions().add(correctAnswer); // For short answer, we add the answer directly.

        // Set the correct option to 1 since there is only one correct answer for short answer questions.
        question.setCorrectOption(1);

        exam.addQuestion(question);
    }
}