package com.example.spottersdispatch;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Accepted_Adapter extends RecyclerView.Adapter<Accepted_Adapter.viewHolder> {

    private java.util.List<Product> List;
    private onItemClickListener clickListener;


    public Accepted_Adapter(onItemClickListener clickListener, java.util.List<Product> List) {
        this.List = List;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public Accepted_Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accepted_design, parent, false);
        return new viewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Accepted_Adapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.ordernumb.setText(List.get(position).getOrder_id());
        holder.receipentname.setText(List.get(position).getReceipient_name());
        holder.receipent_phone.setText(List.get(position).getReceipient_Phone());
        holder.deliveryLocation.setText(List.get(position).getDestination());

        holder.order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClickproduct(List.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView ordernumb, receipentname, receipent_phone, deliveryLocation, order_details;
        Button order_btn, removebtn;

        public viewHolder(@NonNull View view, Accepted_Adapter.onItemClickListener clickListener) {
            super(view);
            ordernumb = view.findViewById(R.id.order_id);
            receipentname = view.findViewById(R.id.receipent_name);
            receipent_phone = view.findViewById(R.id.receipent_phone);
            deliveryLocation = view.findViewById(R.id.deliverylocation);
            order_btn = view.findViewById(R.id.order_btn);


        }


    }


    public interface onItemClickListener {
        void onItemClickproduct(Product product);

    }
}
