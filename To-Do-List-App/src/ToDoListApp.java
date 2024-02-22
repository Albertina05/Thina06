
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

public class ToDoListApp {
    private JFrame frame;
    private JList<Task> taskList;
    private TaskListModel taskListModel;
    private JButton addButton;
    private List<Task> taskListItems;

    public ToDoListApp() {
        frame = new JFrame("To-Do List App");
        taskListModel = new TaskListModel();
        taskList = new JList<>(taskListModel);
        taskList.setCellRenderer(new TaskListCellRenderer());
        addButton = new JButton("Add Task");

        loadTasksFromFile("tasks.txt");

        setupTaskListListeners();
        setupAddButtonListener();

        frame.setLayout(new BorderLayout());
        frame.add(new JLabel("Task List", SwingConstants.CENTER), BorderLayout.NORTH);
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);
        frame.add(addButton, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Create File menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // Create Save option in the File menu
        JMenuItem saveMenuItem = new JMenuItem("Save as JSON");
        fileMenu.add(saveMenuItem);

        // Create Load option in the File menu
        JMenuItem loadMenuItem = new JMenuItem("Load from JSON");
        fileMenu.add(loadMenuItem);

        // Create Sort menu
        JMenu sortMenu = new JMenu("Sort");
        menuBar.add(sortMenu);

        // Create Sort by Due Date option in the Sort menu
        JMenuItem sortByDueDateMenuItem = new JMenuItem("Sort by Due Date");
        sortMenu.add(sortByDueDateMenuItem);

        // Create Sort by Priority option in the Sort menu
        JMenuItem sortByPriorityMenuItem = new JMenuItem("Sort by Priority");
        sortMenu.add(sortByPriorityMenuItem);
        
        
        // Add action listeners to menu items
        saveMenuItem.addActionListener(e -> saveTasksToJson());
        loadMenuItem.addActionListener(e -> loadTasksFromJson());
        sortByDueDateMenuItem.addActionListener(e -> sortTasksByDueDate());
        sortByPriorityMenuItem.addActionListener(e -> sortTasksByPriority());

    }
    
    private void saveTasksToJson() {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showSaveDialog(frame);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(selectedFile)) {
//                 Convert taskListModel's tasks to JSON and save to the selected file
                Gson gson = new Gson();
                String json = gson.toJson(taskListModel.getTasks());
                writer.write(json);
                System.out.println("Tasks saved to JSON file: " + selectedFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

private void loadTasksFromJson() {
    JFileChooser fileChooser = new JFileChooser();
    int choice = fileChooser.showOpenDialog(frame);
    if (choice == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        try (FileReader reader = new FileReader(selectedFile)) {
            // Read JSON data and convert it back to a List<Task>
            Gson gson = new Gson();
            List<Task> loadedTasks = gson.fromJson(reader, new TypeToken<List<Task>>() {}.getType());

            // Clear existing tasks and add loaded tasks to the model
            taskListModel.getTasks().clear();
            taskListModel.getTasks().addAll(loadedTasks);

            // Notify listeners that the model's contents have changed
            taskListModel.fireIntervalRemoved(0, taskListModel.getSize() - 1);
            taskListModel.fireIntervalAdded(0, taskListModel.getSize() - 1);

            System.out.println("Tasks loaded from JSON file: " + selectedFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}







private void sortTasksByDueDate() {
    Collections.sort(taskListModel.getTasks(), (task1, task2) -> task1.getTaskDueDate().compareTo(task2.getTaskDueDate()));

    // Notify the JList that all elements have changed positions
    int size = taskListModel.getSize();
    for (int i = 0; i < size; i++) {
        taskListModel.fireContentsChangedForIndex(i);
    }

    System.out.println("Tasks sorted by Due Date.");
}

private void sortTasksByPriority() {
    // Sort tasks by priority in ascending order
    Collections.sort(taskListModel.getTasks(), Comparator.comparingInt(Task::getTaskPriority));
    
    // Notify the JList that all elements have changed positions
    int size = taskListModel.getSize();
    for (int i = 0; i < size; i++) {
        taskListModel.fireContentsChangedForIndex(i);
    }

    System.out.println("Tasks sorted by Priority (ascending).");
}


    private void setupTaskListListeners() {
        
        taskList.addMouseListener(new MouseAdapter() {
            
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Event" + e);
            if (e.getClickCount() == 1) { // Check for a single click
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Task selectedTask = taskListModel.getElementAt(selectedIndex);

                    // Open the EditTaskFrame and wait for it to close
                    EditTaskFrame editTaskFrame = new EditTaskFrame(selectedTask, selectedIndex);
                    editTaskFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            handleTaskResult(editTaskFrame);
                        }
                    });
                }
            }
        }
    });

        taskList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int selectedIndex = taskList.getSelectedIndex();
                    Task selectedTask = taskListModel.getElementAt(selectedIndex);

                    // Open the EditTaskFrame and wait for it to close
                    EditTaskFrame editTaskFrame = new EditTaskFrame(selectedTask, selectedIndex);
                    editTaskFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            handleTaskResult(editTaskFrame);
                        }
                    });
                }
            }
        });

        taskList.setFocusable(true);
    }

    private void setupAddButtonListener() {
        addButton.addActionListener(e -> openEditTaskFrame(null, -1));
    }

    private void openEditTaskFrame(Task task, int index) {        
        Task taskToEdit;
        if (task == null) {
            // Creating a new task
            taskToEdit = new Task("", "", 1, "To Do", new Date(), new Date());
        } else {
            // Editing an existing task
            taskToEdit = task;
        }
        EditTaskFrame editTaskFrame = new EditTaskFrame(taskToEdit, index);
        editTaskFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                handleTaskResult(editTaskFrame);
            }
        });
    }
    
    private void handleTaskResult(EditTaskFrame editTaskFrame) {
        Task editedTask = editTaskFrame.getEditedTask();
        int selectedIndex = editTaskFrame.getTaskIndex();
        boolean isTaskDeleted = editTaskFrame.getIsTaskDeleted();
        if (editedTask != null) {
             if (selectedIndex != -1) {
                taskListModel.updateTask(selectedIndex, editedTask);
                System.out.println("task updated"); 
            } else {
                taskListModel.addTask(editedTask);
                System.out.println("task added"); 
            }
        }else {
            if (isTaskDeleted) {
                taskListModel.removeTask(selectedIndex);
                System.out.println("task deleted");
            }
        }
        saveTasksToFile("tasks.txt");
    }

    private void loadTasksFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = Task.parseFromLine(line);
                    taskListModel.addTask(task);
                } catch (IllegalArgumentException e) {
                    // Handle invalid task data
                    System.err.println("Error loading task: " + e.getMessage());
                }
            }
            reader.close();
        } catch (IOException e) {
            // Handle file reading error
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private void saveTasksToFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (Task task : taskListModel.getTasks()) {
                writer.write(task.convertToLine());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            // Handle file writing error
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoListApp::new);
    }
}