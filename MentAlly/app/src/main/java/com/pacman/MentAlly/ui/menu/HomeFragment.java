package com.pacman.MentAlly.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.pacman.MentAlly.R;

import java.util.Calendar;
import java.util.Random;

public class HomeFragment extends Fragment {

    private TextView quote;
    private QuotesModel mQuotes = new QuotesModel();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.HOUR_OF_DAY);
        View root;

        //set background based on time of day
        if(time >= 5 && time < 11){
            root = inflater.inflate(R.layout.frag_home_dawn, container, false);
            quoteAnimation(root);
        }
        else if(time >= 11 && time < 18){
            root = inflater.inflate(R.layout.frag_home_day, container, false);
            quoteAnimation(root);
        }
        else if(time >= 18 && time < 22){
            root = inflater.inflate(R.layout.frag_home_sunset, container, false);
            quoteAnimation(root);
        }else {
            root = inflater.inflate(R.layout.frag_home_night, container, false);
            quoteAnimation(root);
        }

        return root;
    }

    public void quoteAnimation(View root){
        quote = (TextView) root.findViewById(R.id.motivational_quote);
        setQuote();
        Animation aniFade = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        quote.startAnimation(aniFade);
    }

    public void setQuote(){
        Random num = new Random();
        int quoteNum = num.nextInt(mQuotes.getLength());
        quote.setText(mQuotes.getQuote(quoteNum));
    }

}
