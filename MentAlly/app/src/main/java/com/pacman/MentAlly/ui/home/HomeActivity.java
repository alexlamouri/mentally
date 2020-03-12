package com.pacman.MentAlly.ui.menu;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pacman.MentAlly.R;

import java.util.Calendar;

public class HomeActivity extends MainActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);

        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.HOUR_OF_DAY);

        //set background based on time of day
        if(time >= 5 && time < 11){
            getLayoutInflater().inflate(R.layout.frag_home_dawn, contentFrameLayout);
        }
        else if(time >= 11 && time < 18){
            getLayoutInflater().inflate(R.layout.frag_home_day, contentFrameLayout);
        }
        else if(time >= 18 && time < 22){
            getLayoutInflater().inflate(R.layout.frag_home_sunset, contentFrameLayout);
        }else {
            getLayoutInflater().inflate(R.layout.frag_home_night, contentFrameLayout);
        }
    }
}
