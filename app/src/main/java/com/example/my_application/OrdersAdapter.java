package com.example.my_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder>{

    private List<Order> lOrders;

    public OrdersAdapter(List<Order> lOrders){
        this.lOrders = lOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.rv_item_order, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Order order = lOrders.get(position);

        // Set item views based on your views and data model
        ImageView imgProfile = holder.imgProfile;
        holder.tvOrder.setText("Order:");
        holder.tvOrderId.setText("#"+order.getO_id());
        holder.tvRequesterName.setText(order.getRequesterName());
        holder.tvVeg.setText(order.getV_qty()+" veg available");
        holder.tvNonVeg.setText(order.getNv_qty()+" non veg available");

    }

    @Override
    public int getItemCount() {
        return lOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgProfile;
        public TextView tvOrder, tvOrderId, tvRequesterName, tvVeg, tvNonVeg, tvRequesterRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProfile = itemView.findViewById(R.id.imgProfile);
            tvOrder =  itemView.findViewById(R.id.tvOrder);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvRequesterName = itemView.findViewById(R.id.tvRequesterName);
            tvVeg = itemView.findViewById(R.id.tvVeg);
            tvNonVeg = itemView.findViewById(R.id.tvNonVeg);
            tvRequesterRating = itemView.findViewById(R.id.tvRequesterRating);
        }
    }
}
