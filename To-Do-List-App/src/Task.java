
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private String taskTitle;
    private String taskDescription;
    private int taskPriority;
    private String taskStatus;
    private Date taskDueDate;
    private Date taskCreationDate;

    public Task(String title, String description, int priority, String status, Date dueDate, Date creationDate) {
        this.taskTitle = title;
        this.taskDescription = description;
        this.taskPriority = priority;
        this.taskStatus = status;
        this.taskDueDate = dueDate;
        this.taskCreationDate = creationDate;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(Date taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public Date getTaskCreationDate() {
        return taskCreationDate;
    }

    public void setTaskCreationDate(Date taskCreationDate) {
        this.taskCreationDate = taskCreationDate;
    }

    private static Date parseDate(String dateString) throws IllegalArgumentException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString);
        }
    }

    @Override
    public String toString() {
        return taskTitle;
    }

    public static Task parseFromLine(String line) throws IllegalArgumentException {
        String[] parts = line.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }

        String title = parts[0];
        String description = parts[1];
        int priority = Integer.parseInt(parts[2]);
        String status = parts[3];
        Date dueDate = parseDate(parts[4]);
        Date creationDate = parseDate(parts[5]);

        return new Task(title, description, priority, status, dueDate, creationDate);
    }

    public String convertToLine() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return taskTitle + "," + taskDescription + "," + taskPriority + "," + taskStatus + "," +
                dateFormat.format(taskDueDate) + "," + dateFormat.format(taskCreationDate);
    }
}