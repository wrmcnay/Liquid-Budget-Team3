package com.example.liquidbudget.ui.DataAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liquidbudget.R;
import com.example.liquidbudget.data.entities.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder>{

    private List<Expense> expenses = new ArrayList<>();

    @NonNull
    @Override
    public ExpenseAdapter.ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expenses, parent, false);
        return new ExpenseAdapter.ExpenseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ExpenseHolder holder, int position) {
        Expense currentExpense = expenses.get(position);
        holder.textViewExpID.setText(String.valueOf(currentExpense.getExpenseID()));
        holder.textViewExpName.setText(currentExpense.getExpenseName());
        holder.textViewCatName.setText(currentExpense.getCategoryName());
        holder.textViewAmount.setText(String.valueOf(currentExpense.getAmount()));
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    class ExpenseHolder extends RecyclerView.ViewHolder {
        private TextView textViewExpID;
        private TextView textViewExpName;
        private TextView textViewCatName;
        private TextView textViewAmount;

        public ExpenseHolder(View itemView) {
            super(itemView);
            textViewExpID = itemView.findViewById(R.id.text_view_expid);
            textViewExpName = itemView.findViewById(R.id.text_view_expname);
            textViewCatName = itemView.findViewById(R.id.text_view_catname);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
        }
    }
}
