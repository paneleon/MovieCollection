package com.example.moviecollection.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecollection.R;
import com.example.moviecollection.model.Movie;
import com.example.moviecollection.views.MovieFragment;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    public enum ListType {
        SAVED,
        FAVORITES,
        WATCHED,
        RECOMMENDATIONS
    }

//    private String[] movies;
    private ArrayList<Movie> movies;
    ListType listType = ListType.SAVED;

    private ItemClickListener clickListener;

//    public MovieListAdapter(String[] options){
//        this.movies = options;
//    }

    public MovieListAdapter(ArrayList<Movie> movies){
        this.movies = movies;
    }

    public MovieListAdapter(ArrayList<Movie> movies, ListType listType){
        this.movies = movies;
        this.listType = listType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_movie_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie item = movies.get(position);
//        holder.titleTextView.setText((item.getTitle() != null ? item.getTitle() : ""));
//        holder.overviewTextView.setText(item.getOverview() != null ? item.getOverview() : "");
//        holder.yearTextView.setText(item.getReleaseDate() != null ? item.getReleaseDate() : "");
//        holder.ratingTextView.setText(item.getVoteAverage() != 0 ? String.valueOf(item.getVoteAverage()) : "");



        if (item.getTitle() != null) {
            holder.titleTextView.setText(item.getTitle());
        }

        if (item.getOverview() != null) {
            holder.overviewTextView.setText(item.getOverview());
        }

        if (item.getReleaseDate() != null) {
            holder.yearTextView.setText(item.getReleaseDate());
        }

        if (item.getVoteAverage() != 0) {
            holder.ratingTextView.setText(item.getVoteAverage());
        }

        holder.counterTextView.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


//     parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return String.valueOf(movies.get(id));
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView counterTextView;
        TextView titleTextView;
        TextView overviewTextView;
        TextView ratingTextView;
        TextView yearTextView;

        ImageButton addButton;
        ImageButton seenButton;
        ImageButton favoritesButton;
        ImageButton deleteButton;

        ViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.movie_title);
            overviewTextView = itemView.findViewById(R.id.movie_overview);
            ratingTextView = itemView.findViewById(R.id.movie_rating);
            yearTextView = itemView.findViewById(R.id.movie_year);
            counterTextView = itemView.findViewById(R.id.movie_item_counter);

            addButton = itemView.findViewById(R.id.movie_item_add_button);
            seenButton = itemView.findViewById(R.id.movie_item_seen_button);
            favoritesButton = itemView.findViewById(R.id.movie_item_favorite_button);
            deleteButton = itemView.findViewById(R.id.movie_item_remove_button);

            switch(listType){
                case SAVED:
                    addButton.setVisibility(View.INVISIBLE);
                    break;
                case RECOMMENDATIONS:
                    seenButton.setVisibility(View.INVISIBLE);
                    favoritesButton.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.INVISIBLE);
                    break;
                case FAVORITES:
                    addButton.setVisibility(View.INVISIBLE);
                    seenButton.setVisibility(View.INVISIBLE);
                    favoritesButton.setVisibility(View.INVISIBLE);
                    break;
                case WATCHED:
                    addButton.setVisibility(View.INVISIBLE);
                    seenButton.setVisibility(View.INVISIBLE);
                    break;
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}
