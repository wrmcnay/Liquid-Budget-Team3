package com.example.liquidbudget.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.liquidbudget.R;

import java.util.Calendar;

public class NotifsDialogue extends AppCompatDialogFragment {

    Spinner frequency;
    TimePicker timePicker;
    private NotifsDialogueListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_notifs_dialogue, null);
        builder.setView(view)
                .setTitle("Notification Setup")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.cancelDialogue();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        cal.set(Calendar.MINUTE, timePicker.getMinute());
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        long timeSet = cal.getTimeInMillis();
                        if(frequency.getSelectedItem().toString().toLowerCase().equals("week")) {
                            listener.setNotifSettings(timeSet, 7);
                        } else {
                            listener.setNotifSettings(timeSet, 1);
                        }
                    }
                });


        frequency = view.findViewById(R.id.spinner_notif_freq);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.notif_frequency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequency.setAdapter(adapter);
        timePicker = view.findViewById(R.id.timePicker1);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener =  (NotifsDialogueListener) context;
        } catch (ClassCastException e) {
            Log.d("ClassCastException", e.getMessage());
        }

    }

    public interface NotifsDialogueListener {
        void cancelDialogue();
        void setNotifSettings(long time, int frequency);
    }


}
