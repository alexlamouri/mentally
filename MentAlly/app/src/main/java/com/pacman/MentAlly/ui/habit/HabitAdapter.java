package com.pacman.MentAlly.ui.habit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pacman.MentAlly.R;

import java.util.ArrayList;

class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitView> {

    private Context mContext;
    private ArrayList<String> habitNames;

    public HabitAdapter(Context context, ArrayList<String> habits) {
        mContext = context;
        habitNames = habits;
    }

    @NonNull
    @Override
    public HabitView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_habit, parent, false);
        HabitView holder = new HabitView(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HabitView holder, int position) {
        holder.habitName.setText(habitNames.get(position));
    }

    @Override
    public int getItemCount() {
        return habitNames.size();
    }

    class HabitView extends RecyclerView.ViewHolder {

        private ConstraintLayout layout;
        private TextView habitName;
        private ProgressBar progress;

        public HabitView(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.parent_layout);
            habitName = itemView.findViewById(R.id.habitname);
            progress = itemView.findViewById(R.id.progressBar);
        }
    }
}
