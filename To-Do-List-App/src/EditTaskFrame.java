
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class EditTaskFrame extends JFrame {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<String> priorityComboBox;
    private JComboBox<String> statusComboBox;
    private JSpinner dueDateSpinner;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private boolean taskDeleted = false;
    private Task editedTask;
    private int taskIndex;

    public EditTaskFrame(Task task, int index) {
        if (task.getTaskTitle().equals("") 
                && task.getTaskDescription().equals("") 
                && task.getTaskStatus().equals("To Do")) {
            setTitle("Add Task");
        } else {
            setTitle("Edit Task");
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        taskIndex = index;

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(new JLabel("Task Title:"), gbc);

        titleField = new JTextField(20);
        titleField.setText(task.getTaskTitle());
        gbc.gridx = 1;
        contentPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(new JLabel("Description:"), gbc);

        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setText(task.getTaskDescription());
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        gbc.gridx = 1;
        contentPanel.add(descriptionScrollPane, gbc);

        String[] priorityOptions = {"Low", "Medium", "High"};
        priorityComboBox = new JComboBox<>(priorityOptions);
        priorityComboBox.setSelectedIndex(task.getTaskPriority() - 1);
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPanel.add(new JLabel("Priority:"), gbc);
        gbc.gridx = 2;
        contentPanel.add(priorityComboBox, gbc);

        String[] statusOptions = {"To Do", "In Progress", "Done"};
        statusComboBox = new JComboBox<>(statusOptions);
        statusComboBox.setSelectedItem(task.getTaskStatus());
        gbc.gridx = 1;
        gbc.gridy = 3;
        contentPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 2;
        contentPanel.add(statusComboBox, gbc);

        dueDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dueDateSpinner, "yyyy-MM-dd");
        dueDateSpinner.setEditor(dateEditor);
        dueDateSpinner.setValue(task.getTaskDueDate());
        gbc.gridx = 1;
        gbc.gridy = 4;
        contentPanel.add(new JLabel("Due Date:"), gbc);
        gbc.gridx = 2;
        contentPanel.add(dueDateSpinner, gbc);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        deleteButton = new JButton("Delete"); // Moved delete button creation here
        
        deleteButton.setBackground(new Color(255, 182, 193)); // Light pink
        deleteButton.setForeground(Color.WHITE);
        
        cancelButton.setBackground(new Color(173, 216, 230)); // Light blue
        cancelButton.setForeground(Color.WHITE);
        
        saveButton.setBackground(new Color(144, 238, 144)); // Light green
        saveButton.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(buttonPanel, gbc);


        add(contentPanel, BorderLayout.CENTER);

        // Set save, cancel, and delete button actions
        setupButtonActions();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
       private void deleteTaskFromDataSource(int index) {
        // Implement the logic to delete the task from your data source (e.g., task list or file)
        // You may need to pass your data source (e.g., taskListModel) to this method
        // and update it accordingly.
    }

    private void setupButtonActions() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskTitle = titleField.getText();
                String taskDescription = descriptionArea.getText();
                int selectedPriorityIndex = priorityComboBox.getSelectedIndex();
                int taskPriority = selectedPriorityIndex + 1;
                String taskStatus = (String) statusComboBox.getSelectedItem();
                Date taskDueDate = (Date) dueDateSpinner.getValue();
                Date taskCreationDate = new Date();

                editedTask = new Task(taskTitle, taskDescription, taskPriority, taskStatus, taskDueDate, taskCreationDate);
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(EditTaskFrame.this, "Are you sure you want to delete this task?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
               if (choice == JOptionPane.YES_OPTION) {
                    taskDeleted = true; // Mark the task for deletion
                    dispose();
                }
            }
        });
    }

    public Task getEditedTask() {
        return editedTask;
    }
    
    public boolean getIsTaskDeleted() {
        return taskDeleted;
    }

    public int getTaskIndex() {
        return taskIndex;
    }
}
