package com.pacman.MentAlly.ui.home;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.menu.WallpaperFragment;

import java.util.Calendar;

public class HomeActivity extends MainActivity {
    private int theme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);

        WallpaperFragment w = new WallpaperFragment();
        this.theme = w.theme;
        getLayoutInflater().inflate(R.layout.wallpaperlayout, contentFrameLayout);
        if (this.theme != -1) {
            AppCompatImageView view = findViewById(R.id.imageView);
            view.setImageResource(this.theme);
        }
    }
}
