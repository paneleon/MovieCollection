package com.example.moviecollection.model;


import java.util.List;

public class Movie {

    boolean adult;
    List<Object> genres;
    int id;
    String imdbId;
    String overview;
    int popularity;
    String releaseDate;
    int revenue;
    int runtime;
    String tagline;
    String title;
    double voteAverage;
    int voteCount;
    String poster;

    String key;

    boolean favorite = false;
    boolean watched = false;

    public Movie(){

    }

    public Movie(int id, String imdbId, String title, String overview){
        this.id = id;
        this.imdbId = imdbId;
        this.title = title;
        this.overview = overview;
    }

    public Movie(int id, String title, String overview){
        this.id = id;
        this.title = title;
        this.overview = overview;
//        this.poster = poster;
    }

    public Movie(int id, String title, String overview, String date, double rating){
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = date;
        this.voteAverage = rating;
    }

    public Movie(String key, int id, String title, String overview, String date, double rating){
        this.key = key;
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = date;
        this.voteAverage = rating;
    }

    public Movie(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return "Movie: title - " + title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public void setFavorite(boolean fav){
        this.favorite = fav;
    }

    public void setWatched(boolean watched){
        this.watched = watched;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public double getVoteAverage(){
        return voteAverage;
    }

    public String getKey(){
        return key;
    }
    public void setKey(String key){
        this.key = key;
    }
}
