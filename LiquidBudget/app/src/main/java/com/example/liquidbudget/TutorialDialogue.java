package com.example.liquidbudget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TutorialDialogue extends DialogFragment {
    private String body;
    TutorialDialogListener listener;
    int currentLayout;
    String positiveButtonText = "YES";
    String negativeButtonText = "NO";

    public interface TutorialDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    public void setBody(String s){ body = s; }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialog);
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(currentLayout, null))
            .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onDialogPositiveClick(TutorialDialogue.this);
                }
            })
            .setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onDialogNegativeClick(TutorialDialogue.this);
                }
            });


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (TutorialDialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + " must implement TutorialDialogListener");
        }
    }

    public void setCurrentLayout(int layout) {
        currentLayout = layout;
    }

    public void setPositiveButtonText(String s){
        positiveButtonText = s;
    }

    public void setNegativeButtonText(String s){
        negativeButtonText = s;
    }
}
