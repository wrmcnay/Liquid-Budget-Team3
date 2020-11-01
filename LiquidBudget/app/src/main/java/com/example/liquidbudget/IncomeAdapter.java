package com.example.liquidbudget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liquidbudget.R;
import com.example.liquidbudget.data.model.Income;

import java.util.ArrayList;
import java.util.List;

public class IncomeAdapter  extends RecyclerView.Adapter<IncomeAdapter.IncomeHolder> {

    private List<Income> incomes = new ArrayList<>();

    @NonNull
    @Override
    public IncomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incomes_list, parent, false);
        return new IncomeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeHolder holder, int position) {
        Income currentIncome = incomes.get(position);
        holder.textViewIncID.setText(String.valueOf(currentIncome.getIncomeID()));
        holder.textViewIncName.setText(String.valueOf(currentIncome.getIncomeName()));
        holder.textViewCatName.setText(String.valueOf(currentIncome.getCategoryName()));
        holder.textViewAmount.setText(String.valueOf(currentIncome.getAmount()));
    }


    @Override
    public int getItemCount() {
        return incomes.size();
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
        notifyDataSetChanged();
    }

    class IncomeHolder extends RecyclerView.ViewHolder {
        private TextView textViewIncID;
        private TextView textViewIncName;
        private TextView textViewCatName;
        private TextView textViewAmount;

        public IncomeHolder(View itemView) {
            super(itemView);
            textViewIncID = itemView.findViewById(R.id.text_view_incid);
            textViewIncName = itemView.findViewById(R.id.text_view_incname);
            textViewCatName = itemView.findViewById(R.id.text_view_catname);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
        }
    }
}