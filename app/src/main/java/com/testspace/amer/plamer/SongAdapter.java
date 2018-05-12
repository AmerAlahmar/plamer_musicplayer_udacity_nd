package com.testspace.amer.plamer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private ArrayList<Song> songList = new ArrayList<>();
    private OnSongSelectedListener listener;

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.songs_list_res_view, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }

    private OnSongSelectedListener getListener() {
        return listener;
    }

    public void setListener(OnSongSelectedListener listener) {
        this.listener = listener;
    }

    class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView albumImageView;
        TextView songNameTextView;
        View view;

        SongViewHolder(View view) {
            super(view);
            this.view = view;
            albumImageView = view.findViewById(R.id.albumImageView);
            songNameTextView = view.findViewById(R.id.songNameTextView);
        }

        void bind(final Song song) {
            albumImageView.setImageResource(R.drawable.defaultalbum);
            songNameTextView.setText(song.getSongName());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getListener() != null) {
                        getListener().onItemClick(song);
                    }
                }
            });
        }
    }

    interface OnSongSelectedListener {
        void onItemClick(Song selectedSong);
    }
}
