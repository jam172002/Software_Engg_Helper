package org.example;

/**
 * This is the main class of the estimation tool application.
 * It initializes the EstimationTool, displays information, and starts the menu.
 */
public class Main {
    public static void main(String[] args) {
        // Create an instance of the EstimationTool
        EstimationTool tool = new EstimationTool();

        // Display information about the work breakdown structure and total effort
        tool.displayInformation();

        // Show the main menu of the estimation tool
        tool.showMenu();
    }
}
