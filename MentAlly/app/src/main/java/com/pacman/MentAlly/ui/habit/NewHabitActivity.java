package com.pacman.MentAlly.ui.habit;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pacman.MentAlly.R;

public class NewHabitActivity extends AppCompatActivity {

    private EditText habit_name;
    private EditText habit_end_date;
    private Spinner tracking_frequency;
    private Button newHabit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);

        habit_name = findViewById(R.id.habit_name);
        habit_end_date = findViewById(R.id.habit_end_date);
        tracking_frequency = findViewById(R.id.habit_frequency);
        newHabit = findViewById(R.id.add_new_habit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.freq, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tracking_frequency.setAdapter(adapter);

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

    }
}
