package com.example.myapp6.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp6.R;
import com.example.myapp6.model.Property;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private Context context;
    private List<Property> propertyList;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Property property = propertyList.get(position);

        holder.rowName.setText(property.getPropertyName());
        holder.rowRoom.setText(property.getPropertyRoom());
        holder.rowPrice.setText(String.valueOf(property.getPropertyPrice())+" €");

    }


    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView rowName;
        public TextView rowRoom;
        public TextView rowPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rowName = itemView.findViewById(R.id.rowName);
            rowRoom = itemView.findViewById(R.id.rowRoom);
            rowPrice = itemView.findViewById(R.id.rowPrice);


        }
    }
}
