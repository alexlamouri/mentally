package com.pacman.MentAlly.ui.habit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.home.MainActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewHabitActivity extends MainActivity {

    private EditText habitName;
    private EditText habitEndDate;
    private Spinner trackingFrequency;
    private Button newHabit;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ConstraintLayout constraintLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_new_habit, contentFrameLayout);

        constraintLayout = findViewById(R.id.linearLayout2);
        constraintLayout.getBackground().setAlpha(50);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        habitName = findViewById(R.id.habit_name);
        habitEndDate = findViewById(R.id.habit_end_date);
        trackingFrequency = findViewById(R.id.habit_frequency);
        newHabit = findViewById(R.id.add_new_habit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.freq, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trackingFrequency.setAdapter(adapter);

        habitEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment(habitEndDate);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        newHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHabit();
                Intent i = new Intent(getApplicationContext(), HabitTrackerActivity.class);
                startActivity(i);
            }
        });
    }

    private void addHabit() {
        Map<String,String> habit = new HashMap<>();
        habit.put("Habit Name", habitName.getText().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String start = sdf.format(Calendar.getInstance().getTime());
        habit.put("Start Date", start);
        habit.put("End Date", habitEndDate.getText().toString());
        habit.put("Frequency", trackingFrequency.getSelectedItem().toString());
        habit.put("Progress", "0");

        db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("habits").document().set(habit);
    }
}
