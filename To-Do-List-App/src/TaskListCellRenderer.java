
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskListCellRenderer extends JPanel implements ListCellRenderer<Task> {
    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JLabel priorityLabel;
    private JLabel dueDateLabel;
    private JLabel statusLabel;
    private JButton editButton;
     private JButton markAsCompleteButton;
      private JButton deleteTaskButton;

    public TaskListCellRenderer() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5)); // Add spacing/padding

        titleLabel = new JLabel();
        descriptionLabel = new JLabel();
        priorityLabel = new JLabel();
        dueDateLabel = new JLabel();
        statusLabel = new JLabel();

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(5, 1)); // Adjust the number of rows as needed
        textPanel.add(titleLabel);
        textPanel.add(descriptionLabel);
        textPanel.add(priorityLabel);
        textPanel.add(dueDateLabel);
        textPanel.add(statusLabel);

        editButton = new JButton("Edit");
        markAsCompleteButton = new JButton("Mark As Complete");
        deleteTaskButton = new JButton("Delete");
        
        // Set button colors and text color
        editButton.setBackground(new Color(173, 216, 230));
        markAsCompleteButton.setBackground(new Color(144, 238, 144));
        deleteTaskButton.setBackground(new Color(255, 182, 193));
        
        editButton.setForeground(Color.WHITE);
        markAsCompleteButton.setForeground(Color.WHITE);
        deleteTaskButton.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(editButton);
        buttonPanel.add(markAsCompleteButton);
        buttonPanel.add(deleteTaskButton);

        add(textPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Task Editing");
            }
        });
        
        markAsCompleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Mark As Complete");
            }
        });
        
        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete Task");
            }
        });
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Task> list, Task task, int index, boolean isSelected, boolean cellHasFocus) {
        titleLabel.setText("Task Title: " + task.getTaskTitle());
        descriptionLabel.setText("Task Description: " + truncateDescription(task.getTaskDescription(), 50));
        priorityLabel.setText("Priority: " + this.setTaskPriority(task.getTaskPriority()));
        dueDateLabel.setText("Due Date: " + task.getTaskDueDate());
        statusLabel.setText("Task Status: " + task.getTaskStatus());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }

    private String setTaskPriority(int input) {
        if (input == 1) {
            return "Low";
        } else if (input == 2) {
            return "Medium";
        } else {
            return "High";
        }
    }

    private String truncateDescription(String description, int maxLength) {
        if (description.length() > maxLength) {
            return description.substring(0, maxLength) + "...";
        } else {
            return description;
        }
    }

}
