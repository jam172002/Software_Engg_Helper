package org.example;

/**
 * Represents a task in a work breakdown structure.
 */
class Task {
    private final String id; // Unique ID of the task
    private final String description; // Description of the task
    private int effortEstimate; // Estimated effort required for the task

    /**
     * Constructs a new Task with specified details.
     *
     * @param id             The unique ID of the task.
     * @param description    The description of the task.
     * @param effortEstimate The estimated effort required for the task.
     */
    public Task(String id, String description, int effortEstimate) {
        // ID of the parent task (if any)
        this.id = id;
        this.description = description;
        this.effortEstimate = effortEstimate;
    }

    /**
     * Retrieves the unique ID of the task.
     *
     * @return The task ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the effort estimate for the task.
     *
     * @return The task's effort estimate.
     */
    public int getEffortEstimate() {
        return effortEstimate;
    }

    /**
     * Sets the effort estimate for the task.
     *
     * @param effortEstimate The effort estimate to set.
     */
    public void setEffortEstimate(int effortEstimate) {
        this.effortEstimate = effortEstimate;
    }

    /**
     * Generates a string representation of the task.
     *
     * @return A string containing task details.
     */
    @Override
    public String toString() {
        return id + ".\t" + description + ", effort = " + effortEstimate;
    }
}
