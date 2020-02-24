package com.pacman.MentAlly.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pacman.MentAlly.R;

import java.util.Calendar;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.HOUR_OF_DAY);
        View root;

        //set background based on time of day
        if(time >= 5 && time < 11){
            root = inflater.inflate(R.layout.frag_home_dawn, container, false);
        }
        else if(time >= 11 && time < 18){
            root = inflater.inflate(R.layout.frag_home_day, container, false);
        }
        else if(time >= 18 && time < 22){
            root = inflater.inflate(R.layout.frag_home_sunset, container, false);
        }else{
            root = inflater.inflate(R.layout.frag_home_night, container, false);
        }

        return root;
    }
}
