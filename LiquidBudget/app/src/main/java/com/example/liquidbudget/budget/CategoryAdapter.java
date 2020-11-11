package com.example.liquidbudget.budget;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.liquidbudget.R;
import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<BudgetCategory> {

    public CategoryAdapter(Context context, ArrayList<BudgetCategory> catList) {
        super(context, 0, catList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            boolean attachToRoot;
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.budget_category_spinner, parent, false);
        }
        ImageView imageViewCat = convertView.findViewById(R.id.image_view_food);
        TextView textViewCat = convertView.findViewById(R.id.text_view_cat);
        BudgetCategory currentItem = (BudgetCategory) getItem(position);

        if(currentItem != null) {
            imageViewCat.setImageResource(currentItem.getCatImg());
            textViewCat.setText(currentItem.getCatName());
        }
        return convertView;
    }
}

