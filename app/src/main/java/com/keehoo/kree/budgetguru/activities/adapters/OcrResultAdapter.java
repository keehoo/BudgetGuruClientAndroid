package com.keehoo.kree.budgetguru.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;
import com.keehoo.kree.budgetguru.R;

import java.util.ArrayList;
import java.util.Collections;
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

    public OcrResultAdapter(Context context, List<Line> data) {
        this.context = context;
        this.data =

                //data;
                prepareData();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private List<Line> prepareData() {
        List<Line> suma = new ArrayList<>();
        String potentialSum = "";
        for (Line line : data) {
            if (line.getValue().contains("SUMA")
                    || line.getValue().contains("SVMA")) {
                sumaX = line.getBoundingBox().centerX();
                sumaY = line.getBoundingBox().centerY();
            }
        }
        if (sumaX == 0 || sumaY == 0) {
            return data;
        }
        for (Line line : data) {
            if (line.getBoundingBox().centerY() - sumaY < diff*2) { // multiplying just to have a positive value
                suma.clear();
                diff = line.getBoundingBox().centerY() - sumaY;
                potentialSum = line.getValue();
                suma.add(line);
            }
        }
        Toast.makeText(context, "Potential sum : "+potentialSum, Toast.LENGTH_LONG).show();
        if (!"".equals(potentialSum) && !suma.isEmpty()) {

            return suma;
        } else return Collections.emptyList();
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
