package com.example.liquidbudget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liquidbudget.data.Entities.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private List<UserAccount> users = new ArrayList<>();

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_account, parent, false);
        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        UserAccount currentUser = users.get(position);
        holder.textViewUID.setText(String.valueOf(currentUser.getUserID()));
        holder.textViewEmail.setText(currentUser.getEmail());
        holder.textViewUsername.setText(currentUser.getUserName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<UserAccount> users){
        this.users = users;
        notifyDataSetChanged();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private TextView textViewUID;
        private TextView textViewUsername;
        private TextView textViewEmail;

        public UserHolder(View itemView){
            super(itemView);
            textViewUID = itemView.findViewById(R.id.text_view_uid);
            textViewUsername = itemView.findViewById(R.id.text_view_username);
            textViewEmail = itemView.findViewById(R.id.text_view_email);
        }
    }
}
