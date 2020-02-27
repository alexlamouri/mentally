package com.pacman.MentAlly.ui.habit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.pacman.MentAlly.R;

public class HabitTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker);

        initHabitList();
    }

    private void initHabitList() {
        RecyclerView habitList = findViewById(R.id.habitlist);
        HabitAdapter adapter = new HabitAdapter(this);
        habitList.setAdapter(adapter);
        habitList.setLayoutManager(new LinearLayoutManager(this));
    }
}
