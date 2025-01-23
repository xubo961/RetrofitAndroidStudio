package com.xubop961.retrofitexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CoctailAdapter extends RecyclerView.Adapter<CoctailAdapter.ViewHolder> {

    private final Context context;
    private final OnCoctailClickListener listener;
    private List<Drinks.Coctail> coctails;

    public CoctailAdapter(Context context, OnCoctailClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setCoctails(List<Drinks.Coctail> coctails) {
        this.coctails = coctails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coctail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Drinks.Coctail coctail = coctails.get(position);
        holder.name.setText(coctail.coctailName);
        Glide.with(context).load(coctail.coctailImageUrl).into(holder.image);
        holder.itemView.setOnClickListener(v -> listener.onCoctailClick(coctail));
    }

    @Override
    public int getItemCount() {
        return coctails == null ? 0 : coctails.size();
    }

    public interface OnCoctailClickListener {
        void onCoctailClick(Drinks.Coctail coctail);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.coctailNombre);
            image = itemView.findViewById(R.id.coctailImagen);
        }
    }
}