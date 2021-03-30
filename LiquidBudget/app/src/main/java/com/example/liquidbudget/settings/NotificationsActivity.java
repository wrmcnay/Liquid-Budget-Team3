package com.example.liquidbudget.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.liquidbudget.R;
import com.example.liquidbudget.data.viewmodels.UserAccountViewModel;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.concurrent.ExecutionException;

public class NotificationsActivity extends AppBaseActivity {
    SwitchCompat weeklyPushNotifs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Notifications");
        setContentView(R.layout.activity_notifications);

        weeklyPushNotifs = (SwitchCompat) findViewById(R.id.weeklyStatusNotifs);
        try {
            populateWeeklyNotifsSwitch();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        weeklyPushNotifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifsDialogue notifsDialogue = new NotifsDialogue();
                notifsDialogue.show(getSupportFragmentManager(), "Notification Settings");
                setWeeklyNotifsSwitch(weeklyPushNotifs.isChecked());
                configureWeeklyNotifs(weeklyPushNotifs.isChecked());
            }
        });
    }

    private void configureWeeklyNotifs(boolean isChecked) {
        if(isChecked) {
            //turn on weekly notifs
            Log.d("DEBUG", "Flipped switch to on");
            Intent intent = new Intent(NotificationsActivity.this, Receiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationsActivity.this, 0, intent, 0);
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), am.INTERVAL_DAY*7, pendingIntent);
        } else {
            //turn off weekly notifs
            Log.d("DEBUG", "Flipped switch to off");
        }
    }

    private void populateWeeklyNotifsSwitch() throws ExecutionException, InterruptedException {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        boolean checked = userAccountViewModel.getReceiveWeeklyNotifs(account.getId());
        weeklyPushNotifs.setChecked(checked);
    }

    private void setWeeklyNotifsSwitch(boolean isChecked) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        userAccountViewModel.setReceiveWeeklyNotifs(account.getId(), isChecked);
    }
}