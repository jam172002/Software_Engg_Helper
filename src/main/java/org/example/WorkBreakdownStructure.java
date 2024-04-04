package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the work breakdown structure containing tasks.
 */
class WorkBreakdownStructure {
    private final List<Task> tasks;

    /**
     * Constructs a new WorkBreakdownStructure with an empty list of tasks.
     */
    public WorkBreakdownStructure() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the work breakdown structure.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Finds a task in the work breakdown structure by its ID.
     *
     * @param id The ID of the task to find.
     * @return The task with the specified ID, or null if not found.
     */
    public Task findTaskById(String id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    /**
     * Displays the structure of the work breakdown.
     * This method currently prints task details without indentation.
     * You may add logic for indentation based on parent-child relationships here.
     */
    public void displayStructure() {
        for (Task task : tasks) {
            System.out.println(task.toString());
            // Add logic for indentation based on parent-child relationship
        }
    }

    /**
     * Calculates the total effort estimate for all tasks in the work breakdown structure.
     *
     * @return The total effort estimate.
     */
    public int calculateTotalEffort() {
        int totalEffort = 0;
        for (Task task : tasks) {
            totalEffort += task.getEffortEstimate();
        }
        return totalEffort;
    }

    /**
     * Counts the number of tasks with unknown effort estimates in the work breakdown structure.
     *
     * @return The number of unknown tasks.
     */
    public int countUnknownTasks() {
        int unknownTasks = 0;
        for (Task task : tasks) {
            if (task.getEffortEstimate() == 0) {
                unknownTasks++;
            }
        }
        return unknownTasks;
    }
}

