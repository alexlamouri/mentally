package com.pacman.MentAlly.ui.habit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pacman.MentAlly.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HabitTrackerActivity extends AppCompatActivity {

    private static final String TAG = "HabitTrackerActivity";
    private ImageButton newHabit;
    private ArrayList<Habit> habitList = new ArrayList<>();
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        newHabit = findViewById(R.id.new_habit);
        newHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NewHabitActivity.class);
                startActivity(i);
            }
        });
        initHabitList();
    }

    private void initHabitList() {
        db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("habits").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + "=>" + document.getData());
                        Calendar endDate = Calendar.getInstance();
                        Calendar startDate = Calendar.getInstance();
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = new SimpleDateFormat("MM/dd/yyyy").parse(document.getString("Start Date"));
                            date2 = new SimpleDateFormat("MM/dd/yyyy").parse(document.getString("End Date"));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        startDate.setTime(date1);
                        endDate.setTime(date2);
                        Habit habit = new Habit(document.getString("Habit Name"), startDate, endDate, document.getString("Frequency"), Integer.parseInt(document.getString("Progress")));
                        habitList.add(habit);
                    }
                    initRecyclerView();
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void initRecyclerView() {
        Log.d(TAG, "Initialize recycler view");
        RecyclerView habitListView = findViewById(R.id.habitlist);
        HabitAdapter adapter = new HabitAdapter(this, habitList);
        habitListView.setAdapter(adapter);
        habitListView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }
}
