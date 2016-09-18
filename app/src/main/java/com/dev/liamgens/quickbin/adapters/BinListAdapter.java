package com.dev.liamgens.quickbin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.objects.Bin;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by leshan on 9/17/16.
 */
public class BinListAdapter extends RecyclerView.Adapter<BinListAdapter.MyViewHolder>{

    private List<Bin> binList;
    OnItemClickListener itemClickListener;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, distance;
        public ImageView verify, type;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.bin_list_item_title);
            description = (TextView) view.findViewById(R.id.bin_list_item_description);
            distance = (TextView) view.findViewById(R.id.distance);
            verify = (ImageView) view.findViewById(R.id.verify);
            type = (ImageView) view.findViewById(R.id.bin_list_item_type);
        }
    }


    public BinListAdapter(List<Bin> binList) {
        this.binList = binList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bin_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Bin bin = binList.get(position);
        holder.title.setText(bin.get_title());
        holder.description.setText(bin.get_description());
        holder.distance.setText(bin.getDistance() + " mi");

        if(bin.get_verifyCounter() >= 5){
            holder.verify.setColorFilter(Color.parseColor("#4CAF50"));
        }else{
            holder.verify.setColorFilter(Color.parseColor("#000000"));
        }

        switch (bin.get_binType()){
            case 0 :
                Picasso.with(context).load(R.drawable.garbage).into(holder.type);

                break;

            case 1 :
                Picasso.with(context).load(R.drawable.recycling).into(holder.type);

                break;

            case 2 :
                Picasso.with(context).load(R.drawable.recycling_center).into(holder.type);

                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(bin);
            }
        });
    }

    @Override
    public int getItemCount() {
        return binList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Bin bin);
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener){
        this.itemClickListener = onItemClickListener;
    }


}
