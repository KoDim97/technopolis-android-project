package com.example.technopark.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;
import com.example.technopark.dto.Person;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {

    private final List<Person> groups;

    public PersonAdapter(List<Person> groups) {
        this.groups = groups;
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item_row, parent, false);
        return new PersonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
        holder.bind(groups.get(position));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class PersonHolder extends RecyclerView.ViewHolder{
        private ImageView tvAvatar;
        private TextView tvName;
        public PersonHolder(@NonNull View itemView) {
            super(itemView);
            tvAvatar = itemView.findViewById(R.id.person_item__image);
            tvName = itemView.findViewById(R.id.person_item__name);
        }

        public void bind(Person person){
            tvAvatar.setImageResource(person.avatar);
            tvName.setText(person.name);
        }
    }
}
