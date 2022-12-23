package com.example.spottersdispatch;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class history_RecyclerAdapter extends RecyclerView.Adapter<history_RecyclerAdapter.viewholder> {

    private java.util.List<history_Product> List ;
    private ItemClickListener clickListener;


    public history_RecyclerAdapter(java.util.List<history_Product> list, ItemClickListener clickListener) {
        List = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public history_RecyclerAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_design,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull history_RecyclerAdapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.rec_name.setText(List.get(position).getRec_name());
        holder.destination.setText(List.get(position).getDestination());
        holder.order_id.setText(List.get(position).getOrder_id());

        holder.btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(List.get(position));
            }
        });
    }

    @Override
    public int getItemCount() { return List.size(); }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView rec_name,destination,order_id;
        TextView btn_details;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            rec_name = itemView.findViewById(R.id.profilename);
            destination = itemView.findViewById(R.id.destination_his);
            order_id = itemView.findViewById(R.id.order_id_his);
            btn_details = itemView.findViewById(R.id.btn_details);
        }
    }
    public interface ItemClickListener {
        void onItemClick(history_Product product);

    }
}
