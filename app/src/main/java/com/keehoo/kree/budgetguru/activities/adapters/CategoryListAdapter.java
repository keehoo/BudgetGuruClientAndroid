package com.keehoo.kree.budgetguru.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keehoo.kree.budgetguru.R;

import java.util.List;

/**
 * Created by krzysztof on 04.11.2017.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> {

    List<String> listOfCategories;
    Context context;
    LayoutInflater layoutInflater;
    OnItemClickListener onItemClickListener;

    public CategoryListAdapter(Context context, List<String> listOfCategories) {
        this.context = context;
        this.listOfCategories = listOfCategories;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onItemClickListener = onClickListener;
    }

    @Override
    public CategoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.cat_list_item_layout, parent, false);
        return new CategoryListViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(CategoryListViewHolder holder, int position) {
        String item = listOfCategories.get(position);

        holder.currentObject = item;
        holder.currentPosition = position;
        holder.textField.setText(item);
    }

    @Override
    public int getItemCount() {
        if (null != listOfCategories && !listOfCategories.isEmpty()) {
            return listOfCategories.size();
        } else
            return 0;
    }

    class CategoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textField;

        public int currentPosition;
        public String currentObject;
        public CategoryListAdapter adapter;

        public CategoryListViewHolder(View itemView, CategoryListAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            textField = (TextView) itemView.findViewById(R.id.category_item_id);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (adapter.onItemClickListener != null) {
                adapter.onItemClickListener.onClick(currentPosition, currentObject);
            }
        }
    }

    public interface OnItemClickListener {
        void onClick(int position, String object);

    }
}
