package com.keehoo.kree.budgetguru.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;

import java.util.List;

/**
 * Created by krzysztof on 03.12.2017.
 */

public class LocalDatabaseAdapter extends RecyclerView.Adapter<LocalDatabaseAdapter.LocalDatabaseViewHolder> {

    Context context;
    List<BudgetEntryModel> listOfBudgetEntries;
    LayoutInflater layoutInflater;

    public LocalDatabaseAdapter(Context context, List<BudgetEntryModel> listOfBudgetEntries) {
        this.context = context;
        this.listOfBudgetEntries = listOfBudgetEntries;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public LocalDatabaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.budget_entry_item_layout, parent, false);
        return new LocalDatabaseViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(LocalDatabaseViewHolder holder, int position) {
        BudgetEntryModel item = listOfBudgetEntries.get(position);

        if (!listOfBudgetEntries.isEmpty()) {
            holder.value.setText("" + item.getBudgetItem().getValue().doubleValue());
            holder.userId.setText("User Id : " + item.getUser());

        }
    }

    @Override
    public int getItemCount() {
        return listOfBudgetEntries.size();
    }

    class LocalDatabaseViewHolder extends RecyclerView.ViewHolder {

        TextView userId;
        TextView value;
        LocalDatabaseAdapter adapter;

        public LocalDatabaseViewHolder(View itemView, LocalDatabaseAdapter adapter) {
            super(itemView);
            this.adapter = adapter;

            userId = (TextView) itemView.findViewById(R.id.user_id);
            value = (TextView) itemView.findViewById(R.id.budget_item_value);

        }


    }

}
