package com.example.final_mobile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_mobile.R;
import com.example.final_mobile.model.Komik;

import java.util.List;

public class KomikAdapter extends RecyclerView.Adapter<KomikAdapter.ViewHolder> {
    private List<Komik> items;
    private Context context;
    private KomikAdapter.onSelectData onSelectData;

    public interface onSelectData {
        void onSelected(Komik komik);
    }

    public KomikAdapter(List<Komik> items, Context context, KomikAdapter.onSelectData onSelectData) {
        this.items = items;
        this.context = context;
        this.onSelectData = onSelectData;
    }

    @NonNull
    @Override
    public KomikAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_komik, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KomikAdapter.ViewHolder holder, int position) {
        final Komik data = items.get(position);

        if (data.getType().equals("Manhua"))
            holder.tvType.setTextColor(Color.parseColor("#265073"));
        else if (data.getType().equals("Manhwa"))
            holder.tvType.setTextColor(Color.parseColor("#2D9596"));
        else if (data.getType().equals("Manga"))
            holder.tvType.setTextColor(Color.parseColor("#9AD0C2"));

//        Glide.with(context)
//                .load(data.getImage())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .override(Target.SIZE_ORIGINAL)
//                .into(holder.imgPhoto);

        holder.tvTitle.setText(data.getTitle());
        holder.tvType.setText(data.getType());
        holder.tvDate.setText(data.getUpdated());
        holder.cvTerbaru.setOnClickListener(v -> onSelectData.onSelected(data));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvType, tvDate;
        public CardView cvTerbaru;
        public ImageView imgPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvTerbaru = itemView.findViewById(R.id.cvTerbaru);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvType = itemView.findViewById(R.id.tvType);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
