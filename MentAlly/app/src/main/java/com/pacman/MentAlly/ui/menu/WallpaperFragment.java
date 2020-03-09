package com.pacman.MentAlly.ui.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pacman.MentAlly.R;

import java.util.ArrayList;

public class WallpaperFragment extends Fragment {

    private ArrayList<image> image = new ArrayList<>();
    private static final int NUM_COLUMNS = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_wallpaper, container, false);
        getImages(root);
        return root;
    }

    private void getImages(View root){
        Log.d("tag", "initImageBitmaps: preparing bitmaps.");

        image.add(new image(R.drawable.wallpaper1));
        image.add(new image(R.drawable.wallpaper2));
        image.add(new image(R.drawable.wallpaper3));
        image.add(new image(R.drawable.wallpaper4));
        image.add(new image(R.drawable.wallpaper5));
        image.add(new image(R.drawable.wallpaper6));
        image.add(new image(R.drawable.wallpaper7));
        image.add(new image(R.drawable.wallpaper8));
        image.add(new image(R.drawable.wallpaper9));
        image.add(new image(R.drawable.wallpaper10));
        image.add(new image(R.drawable.wallpaper11));
        image.add(new image(R.drawable.wallpaper12));
        image.add(new image(R.drawable.wallpaper13));
        image.add(new image(R.drawable.wallpaper14));
        image.add(new image(R.drawable.wallpaper15));
        image.add(new image(R.drawable.wallpaper16));
        image.add(new image(R.drawable.wallpaper17));
        image.add(new image(R.drawable.wallpaper18));
        image.add(new image(R.drawable.wallpaper19));
        image.add(new image(R.drawable.wallpaper20));
        image.add(new image(R.drawable.wallpaper21));
        image.add(new image(R.drawable.wallpaper22));
        image.add(new image(R.drawable.wallpaper23));
        image.add(new image(R.drawable.wallpaper24));
        image.add(new image(R.drawable.wallpaper25));
        image.add(new image(R.drawable.wallpaper26));
        image.add(new image(R.drawable.wallpaper27));
        image.add(new image(R.drawable.wallpaper28));
        image.add(new image(R.drawable.wallpaper29));
        image.add(new image(R.drawable.wallpaper30));
        image.add(new image(R.drawable.wallpaper31));
        image.add(new image(R.drawable.wallpaper32));
        image.add(new image(R.drawable.wallpaper33));
        image.add(new image(R.drawable.wallpaper34));
        image.add(new image(R.drawable.wallpaper35));
        image.add(new image(R.drawable.wallpaper36));
        image.add(new image(R.drawable.wallpaper37));
        image.add(new image(R.drawable.wallpaper38));
        image.add(new image(R.drawable.wallpaper39));
        image.add(new image(R.drawable.wallpaper40));
        image.add(new image(R.drawable.wallpaper41));


        System.out.println(image);
        initRecyclerView(root);



    }

    private void initRecyclerView(View root){
        Log.d("tag", "initRecyclerView: initializing staggered recyclerview.");

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setItemViewCacheSize(20);
        RecyclerViewAdapter staggeredRecyclerViewAdapter = new RecyclerViewAdapter(this.getContext(), image);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }
}
