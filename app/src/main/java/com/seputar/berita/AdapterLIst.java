package com.seputar.berita;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.view.LayoutInflaterFactory;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterLIst extends RecyclerView.Adapter<AdapterLIst.ViewData> {

    ArrayList<Model> list;
    Context context;

    public AdapterLIst(ArrayList<Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rows, parent,false);
        ViewData data = new ViewData(layout);
        return data;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewData holder, int position) {
        Model md = list.get(position);
        holder.txtTitle_.setText(md.getTitle());
        holder.txtDes_.setText(md.getText());
        Glide.with(context)
                .asBitmap()
                .load(md.getPhoto())
                .into(holder.rowImage_);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewData extends RecyclerView.ViewHolder{
        TextView txtTitle_, txtDes_;
        ImageView rowImage_;
        public ViewData(@NonNull View itemView) {
            super(itemView);

            txtTitle_=(TextView)itemView.findViewById(R.id.txtTitle);
            txtDes_=(TextView)itemView.findViewById(R.id.txtdes);
            rowImage_=(ImageView)itemView.findViewById(R.id.rowImage);
        }
    }
}
