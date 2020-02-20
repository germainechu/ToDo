package model;

import model.exceptions.Invalidprogressexception;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    private List<Todo> tasks;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Task task) {
        if (task == null) {
            throw new NullArgumentException("Task cannot be Null!!!");
        }
        if (!contains(task)) {
            tasks.add(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Task task) {
        if (task == null) {
            throw new NullArgumentException();
        }
        if (contains(task)) {
            tasks.remove(task);
        }
    }

    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    @Override
    public int getEstimatedTimeToComplete() {
        int timetocomplete = 0;
        for (Todo todo : tasks) {
            timetocomplete += todo.getEstimatedTimeToComplete();
        }
        return timetocomplete;
    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completed tasks (rounded down to the closest integer).
    //     returns 100 if this project has no tasks!
//    public int getProgress() {
//        int numerator = getNumberOfCompletedTasks();
//        int denominator = getNumberOfTasks();
//        if (numerator == denominator) {
//            return 100;
//        } else {
//            return (int) Math.floor(numerator * 100.0 / denominator);
//        }
//    }
    public int getProgress() {
        try {
            int numberOfTask = 0;
            int totalProgress = 0;

            for (Todo todo : tasks) {
                totalProgress += todo.getProgress();
                numberOfTask++;
            }
            if (tasks.size() == 0) {
                return 0;
            } else {
                this.progress = Math.floorDiv(totalProgress, numberOfTask);
                return this.progress;
            }
        } catch (Invalidprogressexception ip) {
            ip.printStackTrace();
        }
        return this.progress;
    }
    
    // EFFECTS: returns the number of tasks in this project
    public int getNumberOfTasks() {
        int numberOfTasks = 0;
        for (Todo t: tasks) {
            numberOfTasks++;
        }
        return numberOfTasks;
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
//     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }
    
    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Task task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(description);
    }


    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }

    private class ProjectIterator implements Iterator<Todo> {
        int num = 0;
        int priority = 1;
        int numtask = tasks.size();


        @Override
        public boolean hasNext() {
            return numtask > 0;
        }

        @Override
        public Todo next() {
            while (hasNext()) {
                if (num == tasks.size()) {
                    num = 0;
                    priority++;
                }
                if (prioritymethod(tasks.get(num)) == priority) {
                    numtask--;
                    return tasks.get(num++);
                }
                num++;
            }
            throw new NoSuchElementException();
        }

        private int prioritymethod(Todo t) {
            if (t.getPriority().isImportant() && t.getPriority().isUrgent()) {
                return 1;
            }
            if (t.getPriority().isImportant() && !t.getPriority().isUrgent()) {
                return 2;
            }
            if (!t.getPriority().isImportant() && t.getPriority().isUrgent()) {
                return 3;
            } else {
                return 4;
            }
        }
    }

}