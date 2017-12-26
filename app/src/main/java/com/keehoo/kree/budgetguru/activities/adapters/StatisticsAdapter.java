package com.keehoo.kree.budgetguru.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzysztof on 26.12.2017.
 */

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatsViewHolder> {


    List<BudgetEntryModel> dataList;
    Context context;
    LayoutInflater layoutInflater;

    public StatisticsAdapter(Context context, List<BudgetEntryModel> dataList) {
        this.context = context;
        this.dataList = new ArrayList<>(dataList);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public StatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.stats_item, parent, false);
        return new StatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatsViewHolder holder, int position) {
        BudgetEntryModel currentObject = dataList.get(position);
        holder.statsItem.setText("" + currentObject.getValue() + " PLN");
        holder.category.setText(currentObject.getCategory());
        holder.dateField.setText(currentObject.getDateOfCost());
        holder.timeField.setText(currentObject.getTimeOfCost());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class StatsViewHolder extends RecyclerView.ViewHolder {

        TextView statsItem;
        TextView dateField;
        TextView timeField;
        TextView category;

        public StatsViewHolder(View itemView) {
            super(itemView);
            statsItem = (TextView) itemView.findViewById(R.id.stats_item_id);
            dateField = (TextView) itemView.findViewById(R.id.date_field);
            timeField = (TextView) itemView.findViewById(R.id.time_field);
            category = (TextView) itemView.findViewById(R.id.category);
        }
    }

}
