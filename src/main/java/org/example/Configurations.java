package org.example;

/**
 * Represents configurations for estimating effort.
 */
public class Configurations {
    private int numEstimators; // Number of estimators for effort estimation
    private String reconciliationApproach; // Approach for reconciling effort estimates

    /**
     * Constructs a new Configurations object with default values.
     */
    public Configurations() {
        this.numEstimators = 3; // Default value for number of estimators
        this.reconciliationApproach = "Ask the estimators to discuss"; // Default reconciliation approach
    }

    /**
     * Retrieves the number of estimators.
     *
     * @return The number of estimators.
     */
    public int getNumEstimators() {
        return numEstimators;
    }

    /**
     * Sets the number of estimators.
     *
     * @param numEstimators The number of estimators to set.
     */
    public void setNumEstimators(int numEstimators) {
        this.numEstimators = numEstimators;
    }

    /**
     * Retrieves the reconciliation approach.
     *
     * @return The reconciliation approach.
     */
    public String getReconciliationApproach() {
        return reconciliationApproach;
    }

    /**
     * Sets the reconciliation approach.
     *
     * @param reconciliationApproach The reconciliation approach to set.
     */
    public void setReconciliationApproach(String reconciliationApproach) {
        this.reconciliationApproach = reconciliationApproach;
    }
}
