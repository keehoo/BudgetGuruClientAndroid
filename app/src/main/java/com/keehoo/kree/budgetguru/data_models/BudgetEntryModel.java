package com.keehoo.kree.budgetguru.data_models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by krzysztof on 29.09.2017.
 */
@Entity(tableName = "BudgetEntry")
public class BudgetEntryModel {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "user_id")
    private long user;
    @ColumnInfo(name = "date_of_cost")
    private String dateOfCost;
    @ColumnInfo(name = "time_of_cost")
    private String timeOfCost;

    @ColumnInfo(name = "Category")
    private String category;

    @Embedded()
    private BudgetItem budgetItem;


    @Ignore
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
    @Ignore
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
