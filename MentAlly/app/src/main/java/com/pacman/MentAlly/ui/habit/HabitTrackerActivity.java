package com.pacman.MentAlly.ui.habit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pacman.MentAlly.R;

public class HabitTrackerActivity extends AppCompatActivity {

    private FloatingActionButton newHabit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker);

        newHabit = findViewById(R.id.new_habit);
        newHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        initHabitList();
    }

    private void initHabitList() {
        RecyclerView habitList = findViewById(R.id.habitlist);
        HabitAdapter adapter = new HabitAdapter(this);
        habitList.setAdapter(adapter);
        habitList.setLayoutManager(new LinearLayoutManager(this));
    }
}
