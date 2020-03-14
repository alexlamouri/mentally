package com.pacman.MentAlly.ui.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.pacman.MentAlly.R;

import java.util.Calendar;

public class HomeFragment extends Fragment {
    private int theme;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WallpaperFragment w = new WallpaperFragment();
        this.theme = w.theme;

        Calendar calendar = Calendar.getInstance();
       // int time = calendar.get(Calendar.HOUR_OF_DAY);
        View root;
        root = inflater.inflate(R.layout.wallpaperlayout, container, false);
        if (this.theme != -1) {
            AppCompatImageView i = root.findViewById(R.id.imageView);
            i.setImageResource(theme);
        }
        return root;
    }
}
