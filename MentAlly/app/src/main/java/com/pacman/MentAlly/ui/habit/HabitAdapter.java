package com.pacman.MentAlly.ui.habit;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pacman.MentAlly.R;

class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitView> {

    @NonNull
    @Override
    public HabitView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HabitView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
