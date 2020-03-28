package com.pacman.MentAlly.ui.ambient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacman.MentAlly.R;

import java.util.ArrayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioView> {

    private static final String TAG = "AudioAdapter";
    private ArrayList<AudioFile> audioList;
    private Context mContext;

    public AudioAdapter(Context context, ArrayList<AudioFile> audioFiles) {
        this.mContext = context;
        this.audioList = audioFiles;
    }

    @NonNull
    @Override
    public AudioAdapter.AudioView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_audio, parent, false);
        Log.d(TAG, "New viewholder created");
        return new AudioView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AudioAdapter.AudioView holder, final int position) {
        holder.title.setText(audioList.get(position).getTitle());
        holder.audioLength.setText(audioList.get(position).getAudioLength());
        if (audioList.get(position).isPlaying()) {
            holder.playAudio.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            holder.playAudio.setImageResource(android.R.drawable.ic_media_play);
        }
        holder.playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, audioList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                if (! audioList.get(position).isPlaying()) {
                    audioList.get(position).startPlaying();
                    holder.playAudio.setImageResource(android.R.drawable.ic_media_pause);
                } else {
                    audioList.get(position).stopPlaying();
                    holder.playAudio.setImageResource(android.R.drawable.ic_media_play);
                }
            }
        });
    }

    @Override
    public int getItemCount() { return audioList.size(); }

    class AudioView extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView audioLength;
        private ImageButton playAudio;

        public AudioView(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            audioLength = itemView.findViewById(R.id.audioLength);
            playAudio = itemView.findViewById(R.id.playAudio);
        }
    }
}
