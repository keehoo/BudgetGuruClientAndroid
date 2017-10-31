package com.keehoo.kree.budgetguru.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;
import com.keehoo.kree.budgetguru.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by krzysztof on 12.10.17.
 */

public class OcrResultAdapter extends RecyclerView.Adapter<OcrResultAdapter.OcrViewHolder> {

    public static final String DATE_REGEX_WITH_DASH = ".*2?0?1?[123456789]-1?[1234567890]-[123]?[1234567890].*";
    public static final String DATE_REGEX_WITH_DASH1 = "(20)?\\d?\\d-\\d?\\d-\\d\\d?\\d?\\d?";
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
        OcrResultWrapper result = new OcrResultWrapper();
        List<Line> restul = new ArrayList<>();
        Line sumaLine = null;
        Line dateLine;
        for (Line line : data) {
            Log.d("line", line.getValue());
            if (line.getValue().toUpperCase().contains("SUMA")
                    || line.getValue().contains("SUNA")
                    || line.getValue().contains("SVNA")
                    || line.getValue().contains("Suma")

                    ) {
                sumaLine = line;
                sumaY = sumaLine.getBoundingBox().centerY();
                sumaX = sumaLine.getBoundingBox().centerX();
                // restul.add(sumaLine);
            }
            if (line.getValue().matches("2?0?1?[123456789]\\/1?[1234567890]\\/[123]?[1234567890]")
                    || line.getValue().matches(DATE_REGEX_WITH_DASH)) {
                dateLine = line;

                String mydata = dateLine.getValue();
                Pattern pattern = Pattern.compile(DATE_REGEX_WITH_DASH1);
                Matcher matcher = pattern.matcher(mydata);
                Log.d("ADAPTER", "Checking for matcher find");
                if (matcher.find()) {
                    Log.d("ADAPTER", "matcher find returned true");
                    Log.d("ADAPTER", "Group count : "+matcher.groupCount());
                   // for (int i = 1; i < matcher.groupCount(); i++) {
                        Log.d("ADAPTER", "MATCHER       " + matcher.group(0));
                   // }
                } else {
                    result.setReceiptDate(dateLine.getValue().trim());
                    restul.add(dateLine);
                }
            }
        }
        for (Line line : data) {
            if (line.getBoundingBox().centerY() + 35 > sumaLine.getBoundingBox().centerY() &&
                    line.getBoundingBox().centerY() - 35 < sumaLine.getBoundingBox().centerY()) {
                if (line.equals(sumaLine)) {
                    continue;
                }
                result.setReceiptTotalValue(line.getValue());
                restul.add(line);
            }
        }

        if (null != sumaLine && !restul.isEmpty()) {
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
        //holder.textField.setText(item.getValue() + " x: " + item.getBoundingBox().centerX() + " Y: " + item.getBoundingBox().centerY());
        holder.textField.setText("Suma z paragonu: " + item.getValue());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class OcrViewHolder extends RecyclerView.ViewHolder {

        TextView textField;
        TextView data;
        public int currentPosition;
        public String currentObject;
        public OcrResultAdapter adapter;

        public OcrViewHolder(View itemView, OcrResultAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            textField = (TextView) itemView.findViewById(R.id.item);
            data = (TextView) itemView.findViewById(R.id.data);
        }
    }
}
