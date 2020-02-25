package com.example.myapp6.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp6.DetailsActivity;
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
        //holder.rowRoom.setText(property.getPropertyRoom());
        holder.rowPrice.setText(String.valueOf(property.getPropertyPrice())+" €");

        if (property.getPropertyState().getCharState() == 'M'){
            holder.rowPrice.setTextColor(Color.RED);
        }
        else{
            holder.rowPrice.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView rowName;
        public TextView rowRoom;
        public TextView rowPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            rowName = itemView.findViewById(R.id.rowName);
           //rowRoom = itemView.findViewById(R.id.rowRoom);
            rowPrice = itemView.findViewById(R.id.rowPrice);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Property property = propertyList.get(position);
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("id",property.getPropertyId());
            intent.putExtra("name",property.getPropertyName());
            intent.putExtra("room", property.getPropertyRoom());
            intent.putExtra("state", property.getPropertyState().getDescription());
            intent.putExtra("type", property.getPropertyType().getDescription());
            intent.putExtra("price", property.getPropertyPrice());
            intent.putExtra("InDate",property.getPropertyInDate());
            intent.putExtra("OutDate",property.getPropertyOutDate());

            context.startActivity(intent);
            notifyDataSetChanged();
        }

    }
}
