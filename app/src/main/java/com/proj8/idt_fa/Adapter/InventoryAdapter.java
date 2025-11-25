package com.proj8.idt_fa.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proj8.idt_fa.Model.PlantAssetList.DataPAList;
import com.proj8.idt_fa.R;

import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.viewHolder> {

    public ArrayList<DataPAList> productList;
    Context context;

    public InventoryAdapter(Context applicationContext, ArrayList<DataPAList> productList) {
        this.context= applicationContext;
        this.productList = productList;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inv_row,parent,false);
        viewHolder vh = new viewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        DataPAList item = productList.get(position);
        holder.AssetID.setText(item.getItAssetCode().toString());
        holder.FinanceID.setText(item.getFinanceAssetId().toString());
        holder.ItemName.setText(item.getAssetTypeName().toString());
        holder.Status.setText(item.getStatus().toString());

        holder.cardlayout.setBackgroundColor(item.getStatus().equals("Found")? context.getColor(R.color.found_bg)
                :context.getColor(R.color.not_found_bg));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView AssetID, FinanceID, ItemName, Status;
        LinearLayout cardlayout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            AssetID = (TextView) itemView.findViewById(R.id.assetIDscan);
            FinanceID = (TextView) itemView.findViewById(R.id.financeIDscan);
            ItemName = (TextView) itemView.findViewById(R.id.itemName);
            Status = (TextView) itemView.findViewById(R.id.status);
            cardlayout = (LinearLayout) itemView.findViewById(R.id.cardlayout);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
