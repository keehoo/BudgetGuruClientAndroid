package com.keehoo.kree.budgetguru.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;
import com.keehoo.kree.budgetguru.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzysztof on 12.10.17.
 */

public class OcrResultAdapter extends RecyclerView.Adapter<OcrResultAdapter.OcrViewHolder> {

    private List<Line> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private int sumaX, sumaY;
    private int diff = 25;
    private String sumReceiptValue;

    public OcrResultAdapter(Context context, List<Line> dataOfStuff) {
        this.context = context;
        this.data = //dataOfStuff;

                //data;
                prepareData(dataOfStuff);
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private List<Line> prepareData(List<Line> data) {
        List<Line> restul = new ArrayList<>();
        Line sumaLine = null;
        for (Line line : data) {
            if (line.getValue().contains("SUMA")) {
                sumaLine = line;
                sumaY = sumaLine.getBoundingBox().centerY();
                sumaX = sumaLine.getBoundingBox().centerX();
                restul.add(sumaLine);
            }
        }
        for (Line line : data) {
            if (line.getBoundingBox().centerY() + 35 > sumaLine.getBoundingBox().centerY() &&
                    line.getBoundingBox().centerY() - 35 < sumaLine.getBoundingBox().centerY()) {
                if (line.equals(sumaLine)) {
                    continue;
                }
                restul.add(line);
            }
        }

        if (null!=sumaLine && !restul.isEmpty()) {
            return restul;
        }
        return data;
    }

    @Override
    public OcrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_on_ocr_result_list, parent, false);
        return new OcrViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(OcrViewHolder holder, int position) {
        Line item = data.get(position);
        holder.textField.setText(item.getValue() + " x: " + item.getBoundingBox().centerX() + " Y: " + item.getBoundingBox().centerY());
    }

    @Override
    public int getItemCount() {
        return data.size();
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
