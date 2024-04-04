package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Represents an estimation tool for managing task effort estimates.
 */
public class EstimationTool {
    private final WorkBreakdownStructure wbs; // Work Breakdown Structure instance
    private final Configurations config; // Configuration instance
    private final Scanner scanner; // Scanner for user input

    /**
     * Constructs a new EstimationTool object.
     */
    public EstimationTool() {
        this.wbs = new WorkBreakdownStructure();
        this.config = new Configurations();
        initializeDataFromFile(); // Initialize data from file
        this.scanner = new Scanner(System.in);
    }

    /**
     * Initializes data from a file.
     */
    private void initializeDataFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/org/example/tasks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String parentId = parts[0].trim();
                String id = parts[1].trim();
                String description = parts[2].trim();
                int effortEstimate = 0;
                if (parts.length == 4 && !parts[3].trim().isEmpty()) {
                    effortEstimate = Integer.parseInt(parts[3].trim());
                }
                Task task = new Task(id, description, effortEstimate);
                wbs.addTask(task);
            }
        } catch (IOException e) {
            System.err.println("Error reading data from file: " + e.getMessage());
        }
    }

    /**
     * Displays the main menu and handles user choices.
     */
    public void showMenu() {
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. Estimate effort");
            System.out.println("2. Configure");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");
            choice = getIntInput(1, 3);
            switch (choice) {
                case 1:
                    estimateEffortMenu();
                    break;
                case 2:
                    configureSettingsMenu();
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 3);
        scanner.close();
    }

    /**
     * Displays the menu for estimating effort.
     */
    public void estimateEffortMenu() {
        System.out.print("Enter task ID (upper case alphabet only): ");
        String taskId = getStringInput();
        if (taskId != null) {
            estimateEffort(taskId);
        } else {
            System.out.println("Invalid task ID format. Please enter an upper case alphabet.");
        }
    }

    /**
     * Displays the menu for configuring settings.
     */
    public void configureSettingsMenu() {
        System.out.print("Enter new number of estimators: ");
        int numEstimators = getIntInput(1, Integer.MAX_VALUE);
        config.setNumEstimators(numEstimators);

        System.out.println("Choose reconciliation approach:");
        System.out.println("1. Take the highest estimate");
        System.out.println("2. Take the median estimate");
        System.out.println("3. Ask the estimators to discuss");
        int approachChoice = getIntInput(1, 3);
        String reconciliationApproach;
        switch (approachChoice) {
            case 1:
                reconciliationApproach = "Take the highest estimate";
                break;
            case 2:
                reconciliationApproach = "Take the median estimate";
                break;
            case 3:
                reconciliationApproach = "Ask the estimators to discuss";
                break;
            default:
                System.out.println("Invalid choice. Using default reconciliation approach.");
                reconciliationApproach = config.getReconciliationApproach();
                break;
        }
        config.setReconciliationApproach(reconciliationApproach);
        System.out.println("Configuration updated.");
    }

    /**
     * Estimates effort for a specific task ID.
     *
     * @param taskId The task ID for which effort is estimated.
     */
    public void estimateEffort(String taskId) {
        Task task = wbs.findTaskById(taskId);
        if (task != null) {
            if (task.getEffortEstimate() == 0) {
                int numEstimators = config.getNumEstimators();
                int[] effortEstimates = new int[numEstimators];
                System.out.println("Enter " + numEstimators + " effort estimates:");
                for (int i = 0; i < numEstimators; i++) {
                    System.out.print("Estimate " + (i + 1) + ": ");
                    effortEstimates[i] = getIntInput(Integer.MIN_VALUE, Integer.MAX_VALUE);
                }
                System.out.println("All estimates: ");
                for (int estimate : effortEstimates) {
                    System.out.println(estimate);
                }
                if (checkDifferentEstimates(effortEstimates)) {
                    reconcileEstimates(effortEstimates);
                }
                int newEffortEstimate = calculateReconciledEffort(effortEstimates);
                task.setEffortEstimate(newEffortEstimate);
                System.out.println("Effort estimation complete.");
                displayInformation();
            } else {
                System.out.println("Task already has an effort estimate.");
            }
        } else {
            System.out.println("Task not found.");
        }
    }

    /**
     * Checks if there are different effort estimates in the array.
     *
     * @param estimates The array of effort estimates.
     * @return True if there are different estimates, false otherwise.
     */
    private boolean checkDifferentEstimates(int[] estimates) {
        for (int i = 1; i < estimates.length; i++) {
            if (estimates[i] != estimates[i - 1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reconciles different effort estimates based on user choice.
     *
     * @param estimates The array of effort estimates to reconcile.
     */
    private void reconcileEstimates(int[] estimates) {
        int choice;
        do {
            System.out.println("Different effort estimates detected.");
            System.out.println("Choose reconciliation approach:");
            System.out.println("1. Take the highest estimate");
            System.out.println("2. Take the median estimate");
            System.out.println("3. Ask the estimators to discuss");
            System.out.print("Enter your choice: ");
            choice = getIntInput(1, 3);
            switch (choice) {
                case 1:
                    int highest = Arrays.stream(estimates).max().orElse(0);
                    Arrays.fill(estimates, highest);
                    break;
                case 2:
                    int median = calculateMedian(estimates);
                    Arrays.fill(estimates, median);
                    break;
                case 3:
                    System.out.print("Enter a single revised estimate: ");
                    int revisedEstimate = getIntInput(Integer.MIN_VALUE, Integer.MAX_VALUE);
                    Arrays.fill(estimates, revisedEstimate);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice < 1 || choice > 3);
    }

    /**
     * Calculates the median value of an array of integers.
     *
     * @param array The array of integers.
     * @return The median value.
     */
    private int calculateMedian(int[] array) {
        Arrays.sort(array);
        int middle = array.length / 2;
        if (array.length % 2 == 0) {
            return (array[middle - 1] + array[middle]) / 2;
        } else {
            return array[middle];
        }
    }

    /**
     * Displays the main information about tasks and effort.
     */
    public void displayInformation() {
        wbs.displayStructure();
        System.out.println("Total known effort = " + wbs.calculateTotalEffort());
        System.out.println("Unknown tasks = " + wbs.countUnknownTasks());
    }

    /**
     * Reads an integer input from the user within a specified range.
     *
     * @param min The minimum allowed value.
     * @param max The maximum allowed value.
     * @return The integer input from the user.
     */
    private int getIntInput(int min, int max) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(scanner.next());
                if (input >= min && input <= max) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return input;
    }

    /**
     * Reads a string input from the user.
     *
     * @return The string input from the user.
     */
    private String getStringInput() {
        String input;
        while (true) {
            input = scanner.next();
            if (input.matches("[A-Z]")) {
                break;
            } else {
                System.out.println("Invalid input format. Please try again.");
            }
        }
        return input;
    }

    /**
     * Calculates the reconciled effort based on the provided estimates array.
     *
     * @param estimates An array of effort estimates
     * @return The reconciled effort (average of all estimates)
     */
    private int calculateReconciledEffort(int[] estimates) {
        int sum = 0;
        // Calculate the sum of all estimates
        for (int estimate : estimates) {
            sum += estimate;
        }
        // Calculate the average by dividing the sum by the number of estimates
        return sum / estimates.length;
    }

}
