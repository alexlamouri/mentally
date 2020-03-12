package com.pacman.MentAlly.ui.habit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.pacman.MentAlly.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class HabitTrackerFragment extends Fragment implements Observer, OnCompleteListener<QuerySnapshot> {

        private static final String TAG = "HabitTrackerActivity";
        private ImageButton newHabit;
        private ArrayList<Habit> habitList = new ArrayList<>();
        private FirebaseFirestore db;
        private FirebaseAuth mAuth;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.activity_habit_tracker, container, false);

            db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();

            newHabit = root.findViewById(R.id.new_habit);
            newHabit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), NewHabitActivity.class);
                    startActivity(i);
                }
            });
            db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("habits").get().addOnCompleteListener(this);
            return root;
        }

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
                    Habit habit = new Habit(document.getId(), document.getString("Habit Name"), startDate, endDate, document.getString("Frequency"), Integer.parseInt(document.getString("Progress")));
                    habit.addObserver(this);
                    habitList.add(habit);
                }
                initRecyclerView();
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        }

        private void initRecyclerView() {
            Log.d(TAG, "Initialize recycler view");
            RecyclerView habitListView = getView().findViewById(R.id.habitlist);
            HabitAdapter adapter = new HabitAdapter(getActivity(), habitList);
            habitListView.setAdapter(adapter);
            habitListView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter.notifyDataSetChanged();
        }

        @Override
        public void update(Observable o, Object arg) {
            Habit h = (Habit) arg;
            Map<String, String> progressUpdate = new HashMap<>();
            progressUpdate.put("Progress", Integer.toString(h.getProgress()));
            db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("habits").document(h.getHabitId()).set(progressUpdate, SetOptions.merge());
        }
}

