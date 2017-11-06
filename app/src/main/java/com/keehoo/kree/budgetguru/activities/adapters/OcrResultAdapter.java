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

    public static final String DATE_REGEX_WITH_DASH = ".*2?0?1?[123456789]-1?[1234567890]-[123]?[1234567890].*";
    public static final String DATE_REGEX_WITH_DASH1 = ".*(20)?\\d?\\d-\\d?\\d-\\d\\d?\\d?\\d?.*";
    public static final String DATE_REGEX_WITH_DASH_DATE_ONLY = "(20)?\\d?\\d-\\d?\\d-\\d\\d?\\d?\\d?";
    public static final String TIME_REGEX_WITH_DASH1 = "\\d\\d?[:.]\\d\\d?";
    public static final String TIME_REGEX_WITH_DASH2 = "\\d\\d?.\\d\\d?";
    private List<OcrResultWrapper> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public OcrResultAdapter(Context context, List<OcrResultWrapper> dataOfStuff) {
        this.context = context;
        this.data = dataOfStuff;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public OcrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_on_ocr_result_list, parent, false);
        return new OcrViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(OcrViewHolder holder, int position) {
        OcrResultWrapper item = data.get(position);
        //holder.textField.setText(item.getValue() + " x: " + item.getBoundingBox().centerX() + " Y: " + item.getBoundingBox().centerY());
        holder.textField.setText("Suma z paragonu: " + item.getReceiptTotalValue());
        holder.data.setText("Data: "+ item.getReceiptDate());
        holder.time.setText("Time: "+ item.getReceiptTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class OcrViewHolder extends RecyclerView.ViewHolder {

        TextView textField;
        TextView data;
        TextView time;
        public int currentPosition;
        public String currentObject;
        public OcrResultAdapter adapter;

        public OcrViewHolder(View itemView, OcrResultAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            textField = (TextView) itemView.findViewById(R.id.item);
            data = (TextView) itemView.findViewById(R.id.data);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
