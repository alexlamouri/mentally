package com.pacman.MentAlly.ui.habit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pacman.MentAlly.R;

import java.util.ArrayList;

/**
 * A class that controls the habits that are displayed in the list as it scrolls
 */
class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitView> {

    private static final String TAG = "HabitAdapter";
    private Context mContext;
    private ArrayList<Habit> habitList;

    public HabitAdapter(Context context, ArrayList<Habit> habits) {
        mContext = context;
        habitList = habits;
    }

    @NonNull
    @Override
    public HabitView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_habit, parent, false);
        Log.d(TAG, "New viewholder created");
        return new HabitView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitView holder, final int position) {
        holder.habitName.setText(habitList.get(position).getHabitName());
        holder.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habitList.get(position).incrementProgress();
                notifyItemChanged(position);
            }
        });

        holder.downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, habitList.get(position).getHabitName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.progress.setMax(habitList.get(position).getMaxProgress());
        holder.progress.setProgress(habitList.get(position).getProgress(), true);
        Log.d(TAG, "Viewholder " + habitList.get(position).getHabitName() + " bound.");
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    class HabitView extends RecyclerView.ViewHolder {

        private TextView habitName;
        private ProgressBar progress;
        private ImageButton upButton;
        private ImageButton downButton;

        public HabitView(@NonNull View itemView) {
            super(itemView);
            habitName = itemView.findViewById(R.id.habitname);
            progress = itemView.findViewById(R.id.progressBar);
            upButton = itemView.findViewById(R.id.habityes);
            downButton = itemView.findViewById(R.id.habitno);
        }
    }
}
