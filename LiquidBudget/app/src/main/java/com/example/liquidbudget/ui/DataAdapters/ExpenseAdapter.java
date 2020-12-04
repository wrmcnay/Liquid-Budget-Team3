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
    private static ClickListener clickListener;

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expenses, parent, false);
        return new ExpenseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Expense currentExpense = expenses.get(position);
        double amount = currentExpense.getAmount();
        holder.textViewExpName.setText(currentExpense.getExpenseName());
        holder.textViewCatName.setText(currentExpense.getCategoryName());
        if(amount != 0) {
            holder.textViewAmount.setText(String.format(("%.2f"), currentExpense.getAmount()));
        }
        holder.textViewDate.setText(currentExpense.getDate());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    public Expense getExpenseAtPosition(int position) {
        return expenses.get(position);
    }

    class ExpenseHolder extends RecyclerView.ViewHolder {
        private TextView textViewExpName;
        private TextView textViewCatName;
        private TextView textViewAmount;
        private TextView textViewDate;

        public ExpenseHolder(View itemView) {
            super(itemView);
            textViewExpName = itemView.findViewById(R.id.text_view_expname);
            textViewCatName = itemView.findViewById(R.id.text_view_catname);
            textViewAmount = itemView.findViewById(R.id.text_view_exp_amount);
            textViewDate = itemView.findViewById(R.id.text_view_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ExpenseAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}

