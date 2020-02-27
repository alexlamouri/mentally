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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pacman.MentAlly.R;

import java.util.ArrayList;

public class HabitTrackerActivity extends AppCompatActivity {

    private ImageButton newHabit;
    private ArrayList<String> habitNames;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker);

        db = FirebaseFirestore.getInstance();

        newHabit = findViewById(R.id.new_habit);
        newHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NewHabitActivity.class);
                startActivity(i);
            }
        });
        habitNames = new ArrayList<>();
        initHabitList();
    }

    private void initHabitList() {
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("habits").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot docsnapshot, @Nullable FirebaseFirestoreException e) {
                Log.w("", "Entered sucessful");
                habitNames.clear();

                for (DocumentSnapshot snapshot : docsnapshot) {
                    if (e != null) {
                        Log.w("Listen failed.", e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        habitNames.add(snapshot.getString("Habit Name"));
                        Log.w("", snapshot.getString("Habit Name"));
                        Log.d("", "Current data: " + snapshot.getData());
                    } else {
                        Log.d("", "Current data: null");
                    }
                }
                //adapter.notifyDataSetChanged();
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView habitList = findViewById(R.id.habitlist);
        HabitAdapter adapter = new HabitAdapter(this, habitNames);
        habitList.setAdapter(adapter);
        habitList.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }
}
