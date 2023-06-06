package com.example.yindao.shipeiqi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yindao.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

public class Xin extends RecyclerView.Adapter<Xin.VH> {
    Context context;
    JSONArray jsonArray;

    public Xin(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        holder.tv.setText(jsonArray.optJSONObject(position).optString("name"));
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class VH extends RecyclerView.ViewHolder{
        TextView tv;

        public VH(@NonNull @NotNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
