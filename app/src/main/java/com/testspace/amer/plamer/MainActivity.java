package com.testspace.amer.plamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SongAdapter.OnSongSelectedListener {
    RecyclerView recyclerView;
    SongAdapter songAdapter;
    public static ArrayList<Song> songs = new ArrayList<>();
    public static Song selectedSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAllSongsInRaw();
        recyclerView = findViewById(R.id.songsListRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        songAdapter = new SongAdapter();
        songAdapter.setSongList(songs);
        songAdapter.setListener(this);
        recyclerView.setAdapter(songAdapter);
    }

    public void getAllSongsInRaw() {
        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {
            try {
                songs.add(new Song(field.getName(), getPackageName(), field.getInt(field)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopAllSongs() {
        for (Song song : MainActivity.songs) {
            if (song.isPlaying())
                song.stopPlayer();
        }
    }

    @Override
    public void onItemClick(Song selectedSong) {
        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
        MainActivity.selectedSong = selectedSong;
        startActivity(intent);
    }
}