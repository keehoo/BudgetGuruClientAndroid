package com.keehoo.kree.budgetguru.data_models;

import java.util.Objects;

/**
 * Created by krzysztof on 29.09.2017.
 */

public class BudgetEntryModel {

    BudgetItem budgetItem;
    long user;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetEntryModel that = (BudgetEntryModel) o;
        return user == that.user &&
                Objects.equals(budgetItem, that.budgetItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetItem, user);
    }
}
