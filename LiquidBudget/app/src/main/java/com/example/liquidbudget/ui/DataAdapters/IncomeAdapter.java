package com.example.liquidbudget.ui.DataAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liquidbudget.R;
import com.example.liquidbudget.data.entities.Income;

import java.util.ArrayList;
import java.util.List;

public class IncomeAdapter  extends RecyclerView.Adapter<IncomeAdapter.IncomeHolder> {

    private List<Income> incomes = new ArrayList<>();
    private static ClickListener clickListener;

    @NonNull
    @Override
    public IncomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incomes, parent, false);
        return new IncomeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeHolder holder, int position) {
        Income currentIncome = incomes.get(position);
        holder.textViewIncName.setText(currentIncome.getIncomeName());
        holder.textViewCatName.setText(currentIncome.getCategoryName());
        holder.textViewAmount.setText(String.valueOf(currentIncome.getAmount()));
        holder.textViewDate.setText(currentIncome.getDate());
    }

    @Override
    public int getItemCount() {
        return incomes.size();
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
        notifyDataSetChanged();
    }

    public Income getIncomeAtPosition(int position) {
        return incomes.get(position);
    }

    class IncomeHolder extends RecyclerView.ViewHolder {
        private TextView textViewIncName;
        private TextView textViewCatName;
        private TextView textViewAmount;
        private TextView textViewDate;

        public IncomeHolder(View itemView) {
            super(itemView);
            textViewIncName = itemView.findViewById(R.id.text_view_incname);
            textViewCatName = itemView.findViewById(R.id.text_view_catname);
            textViewAmount = itemView.findViewById(R.id.text_view_inc_amount);
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
        IncomeAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}