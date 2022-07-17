package com.example.moviecollection.model;


import java.util.List;

public class Movie {

    boolean adult;
    List<Object> genres;

//    array[object]
//    optional
//            id
//    integer
//            optional
    int id;
    String imdbId;
    String overview;
    int popularity;
    String releaseDate;
    int revenue;
    int runtime;
    String tagline;
    String title;
    int voteAverage;
    int voteCount;
    String poster;

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
}
