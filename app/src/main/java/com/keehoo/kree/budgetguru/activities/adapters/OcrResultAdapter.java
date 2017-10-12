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
 * Created by krzysztof on 12.10.17.
 */

public class OcrResultAdapter extends RecyclerView.Adapter<OcrResultAdapter.OcrViewHolder> {

    private List<String> dane;
    private LayoutInflater layoutInflater;
    private Context context;

    public OcrResultAdapter(Context context, List<String> dane) {
        this.context = context;
        this.dane = dane;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public OcrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_on_ocr_result_list, parent, false);
        return new OcrViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(OcrViewHolder holder, int position) {
        String item = dane.get(position);
        holder.textField.setText(item);
    }

    @Override
    public int getItemCount() {
        return dane.size();
    }

    class OcrViewHolder extends RecyclerView.ViewHolder {

        public TextView textField;
        public int currentPosition;
        public String currentObject;
        public OcrResultAdapter adapter;

        public OcrViewHolder(View itemView, OcrResultAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            textField = (TextView) itemView.findViewById(R.id.item);
        }
    }
}
