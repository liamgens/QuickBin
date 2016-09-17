package com.dev.liamgens.quickbin.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.objects.Bin;

import java.util.List;

/**
 * Created by leshan on 9/17/16.
 */
public class BinListAdapter extends RecyclerView.Adapter<BinListAdapter.MyViewHolder>{

    private List<Bin> binList;
    OnItemClickListener itemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;
        public ImageView verify;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.bin_list_item_title);
            description = (TextView) view.findViewById(R.id.bin_list_item_description);
            verify = (ImageView) view.findViewById(R.id.verify);
        }
    }


    public BinListAdapter(List<Bin> binList) {
        this.binList = binList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bin_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Bin bin = binList.get(position);
        holder.title.setText(bin.get_title());
        holder.description.setText(bin.get_description());

        if(bin.get_verifyCounter() >= 5){
            holder.verify.setColorFilter(Color.parseColor("#4CAF50"));
        }else{
            holder.verify.setColorFilter(Color.parseColor("#000"));
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
