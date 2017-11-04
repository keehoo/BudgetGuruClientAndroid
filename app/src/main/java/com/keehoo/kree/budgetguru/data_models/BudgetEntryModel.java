package com.keehoo.kree.budgetguru.data_models;

/**
 * Created by krzysztof on 29.09.2017.
 */

public class BudgetEntryModel {

    private Long id;
    private long user;
    private String dateOfCost;
    private String timeOfCost;
    private String category;

    private BudgetItem budgetItem;


    public BudgetEntryModel(BudgetItem budgetItem) {
        this.budgetItem = budgetItem;
        boolean isCost;
        if (budgetItem.getValue().doubleValue() > 0) {
            isCost = true;
        } else isCost = false;
        category = "UNKNOWN";
        dateOfCost = null;
        timeOfCost = null;
    }

    public BudgetEntryModel(BudgetItem budgetItem, String category) {
        setCategory(category);
        this.budgetItem = budgetItem;
        boolean isCost;
        if (budgetItem.getValue().doubleValue() > 0) {
            isCost = true;
        } else isCost = false;
        dateOfCost = null;
        timeOfCost = null;

    }

    public BudgetEntryModel() {

    }

    public double getValue() {
        return budgetItem.getValue().doubleValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public BudgetItem getBudgetItem() {
        return budgetItem;
    }

    public void setBudgetItem(BudgetItem budgetItem) {
        this.budgetItem = budgetItem;
    }

    public String getDateOfCost() {
        return dateOfCost;
    }

    public void setDateOfCost(String dateOfCost) {
        this.dateOfCost = dateOfCost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTimeOfCost() {
        return timeOfCost;
    }

    public void setTimeOfCost(String timeOfCost) {
        this.timeOfCost = timeOfCost;
    }
}
