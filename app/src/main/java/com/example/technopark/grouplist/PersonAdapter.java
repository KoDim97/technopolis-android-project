package com.example.technopark.grouplist;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PersonHolder extends RecyclerView.ViewHolder{

        public PersonHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
