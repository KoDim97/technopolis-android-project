package com.example.technopark.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;
import com.example.technopark.dto.PersonItem;

import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.PersonHolder> {

    private List<PersonItem> members;

    public GroupListAdapter(List<PersonItem> members) {
        this.members = members;
    }

    public void updateList(List<PersonItem> list){
        members = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        return new PersonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
        holder.bind(members.get(position));
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class PersonHolder extends RecyclerView.ViewHolder{
        private ImageView tvAvatar;
        private TextView tvName;
        public PersonHolder(@NonNull View itemView) {
            super(itemView);
            tvAvatar = itemView.findViewById(R.id.person_item__image);
            tvName = itemView.findViewById(R.id.person_item__name);
        }

        public void bind(PersonItem person){
            tvAvatar.setImageResource(person.avatar);
            tvName.setText(person.name);
        }
    }
}
