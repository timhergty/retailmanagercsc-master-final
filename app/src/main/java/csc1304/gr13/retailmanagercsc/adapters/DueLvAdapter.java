package csc1304.gr13.retailmanagercsc.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.modelClass.DueModel;

import java.util.ArrayList;

/**
 * Created by CS1304 on 8/02/2021.
 */

public class DueLvAdapter extends ArrayAdapter<DueModel> {

    private Context context;
    private ArrayList<DueModel> dues;

    public DueLvAdapter(@NonNull Context context, @NonNull ArrayList<DueModel> objects) {
        super(context, R.layout.custom_due_fragment_lv, objects);
        this.context = context;
        dues = objects;
    }
    class ViewHolder{
        TextView dueSerialTv;
        TextView dueAmountTv;
        TextView customerNameDue;
        TextView customerNumberDueTv;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_due_fragment_lv,parent,false);

            holder.dueSerialTv = (TextView) convertView.findViewById(R.id.dueSerialTv);
            holder.dueAmountTv = (TextView) convertView.findViewById(R.id.dueAmountTv);
            holder.customerNameDue = (TextView) convertView.findViewById(R.id.customerNameDue);
            holder.customerNumberDueTv = (TextView) convertView.findViewById(R.id.customerNumberDueTv);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.dueSerialTv.setText(dues.get(position).getSerialNo());
        holder.dueAmountTv.setText("Due : "+dues.get(position).getDueAmount()+" UGX");
        holder.customerNameDue.setText(dues.get(position).getCustomerName());
        holder.customerNumberDueTv.setText(dues.get(position).getCustomerPhone());
        return convertView;

    }
}
