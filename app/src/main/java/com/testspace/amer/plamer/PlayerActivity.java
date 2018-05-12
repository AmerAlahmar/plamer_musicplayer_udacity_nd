package com.testspace.amer.plamer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerActivity extends AppCompatActivity {
    int playingSongsPointer;
    Song selectedSong = MainActivity.selectedSong;
    ArrayList<Song> toPlaySongs;
    Handler SongTimeHandler = new Handler();
    Runnable updateSongTime;
    Handler seekBarHandler = new Handler();
    Runnable updateProgressSeekBar;
    @BindView(R.id.playPauseImageButton)
    ImageButton playPauseImageButton;
    @BindView(R.id.nextImageButton)
    ImageButton nextImageButton;
    @BindView(R.id.prevImageButton)
    ImageButton prevImageButton;
    @BindView(R.id.nowPlayingSongTextView)
    TextView nowPlayingSongTextView;
    @BindView(R.id.timeTextView)
    TextView timeTextView;
    @BindView(R.id.durationTextView)
    TextView durationTextView;
    @BindView(R.id.progressSeekBar)
    SeekBar progressSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        setupAndPlay(selectedSong);
        playPauseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSong.isPlaying()) {
                    playPauseImageButton.setImageResource(R.drawable.play);
                    selectedSong.pausePlayer();
                } else {
                    playPauseImageButton.setImageResource(R.drawable.puse);
                    selectedSong.play();
                }
            }
        });
        toPlaySongs = MainActivity.songs;
        playingSongsPointer = toPlaySongs.indexOf(selectedSong);
        nextImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playingSongsPointer >= 0 && playingSongsPointer < toPlaySongs.size() - 1) {
                    playingSongsPointer++;
                    setupAndPlay(toPlaySongs.get(playingSongsPointer));
                } else {
                    playingSongsPointer = 0;
                    setupAndPlay(toPlaySongs.get(playingSongsPointer));
                }
            }
        });
        prevImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSong.getIntCurrentPosition() / 1000 <= 1) {
                    if (playingSongsPointer > 0 && playingSongsPointer < toPlaySongs.size()) {
                        playingSongsPointer--;
                        setupAndPlay(toPlaySongs.get(playingSongsPointer));
                    } else {
                        playingSongsPointer = toPlaySongs.size() - 1;
                        setupAndPlay(toPlaySongs.get(playingSongsPointer));
                    }
                } else {
                    selectedSong.goTo(0);
                }
            }
        });
        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    selectedSong.goTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void setupAndPlay(Song song) {
        if (!song.equals(Song.getNowPlaying())) {
            MainActivity.stopAllSongs();
            song.startPlayer(this);
            selectedSong = song;
        }
        nowPlayingSongTextView.setText(song.getSongName());
        durationTextView.setText(song.getDuration());
        playPauseImageButton.setImageResource(R.drawable.puse);
        initSongTime();
        initSeekBar();
    }

    public void initSongTime() {
        updateSongTime = new Runnable() {
            @Override
            public void run() {
                timeTextView.setText(selectedSong.getCurrentTime());
                SongTimeHandler.postDelayed(this, 1000);
            }
        };
        SongTimeHandler.post(updateSongTime);
    }

    public void initSeekBar() {
        progressSeekBar.setMax(selectedSong.getIntDuration() / 1000);
        updateProgressSeekBar = new Runnable() {
            @Override
            public void run() {
                progressSeekBar.setProgress(selectedSong.getIntCurrentPosition() / 1000);
                seekBarHandler.postDelayed(this, 1000);
                if (selectedSong.getCurrentTime().equals(selectedSong.getDuration())) {
                    playPauseImageButton.setImageResource(R.drawable.play);
                    progressSeekBar.setProgress(0);
                }
            }
        };
        seekBarHandler.post(updateProgressSeekBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SongTimeHandler.removeCallbacks(updateSongTime);
        seekBarHandler.removeCallbacks(updateProgressSeekBar);
    }
}