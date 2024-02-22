
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TaskListModel extends AbstractListModel<Task> {
    private List<Task> tasks;

    public TaskListModel() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        int index = tasks.size() - 1;
        fireIntervalAdded(this, index, index);
    }

    public void updateTask(int index, Task task) {
        tasks.set(index, task);
        fireContentsChanged(this, index, index);
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            fireIntervalRemoved(this, index, index);
        }
    }
    
    @Override
    public int getSize() {
        return tasks.size();
    }

    @Override
    public Task getElementAt(int index) {
        return tasks.get(index);
    }

    // Custom method to get the list of tasks
    public List<Task> getTasks() {
        return tasks;
    }

    public void fireContentsChangedForIndex(int index) {
        fireContentsChanged(this, index, index);
    }

      // Custom methods to fire interval added and removed
    public void fireIntervalAdded(int startIndex, int endIndex) {
        if (startIndex <= endIndex) {
            fireIntervalAdded(this, startIndex, endIndex);
        }
    }

    public void fireIntervalRemoved(int startIndex, int endIndex) {
        if (startIndex <= endIndex) {
            fireIntervalRemoved(this, startIndex, endIndex);
        }
    }
}
