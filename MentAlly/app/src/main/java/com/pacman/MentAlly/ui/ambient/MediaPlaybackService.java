package com.pacman.MentAlly.ui.ambient;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;

import java.util.List;

public class MediaPlaybackService extends MediaBrowserServiceCompat {

    private static final String TAG = "MediaPlaybackService";
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;

    @Override
    public void onCreate() {
        super.onCreate();

        //Create a Media Session
        mediaSession = new MediaSessionCompat(this, TAG);

        //Enable Callbacks from Media Buttons
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        //Set an initial playback state
        stateBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());

        //Setup callback from controller
        mediaSession.setCallback(new MediaSessionCallBack());

        //Set the session token
        setSessionToken(mediaSession.getSessionToken());
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

    }

    private class MediaSessionCallBack extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            super.onPlay();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onStop() {
            super.onStop();
        }
    }
}
