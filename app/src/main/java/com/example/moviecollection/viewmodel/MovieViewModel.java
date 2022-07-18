package com.example.moviecollection.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviecollection.model.Movie;
import com.example.moviecollection.repositories.MovieRepository;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    public MovieRepository movieRepository;
    private static MutableLiveData<List<Movie>> movies;
    private static final String TAG = "MoviesUpdate";

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public void addMovie(Movie movie){
        movieRepository.addMovie(movie);
    }

//    public ArrayList<Movie> getMovies(DataSnapshot snapshot){
//        return movieRepository.getMovies();
//    }

    public ArrayList<Movie> getArrayOfMovies(DataSnapshot snapshot) {
        return movieRepository.getArrayOfMovies(snapshot);
    }

    public void removeMovie(String key){
        movieRepository.removeMovie(key);
    }

    public static void loadMoviesFromDB() {
//        new AsyncTask<Void, Void, List<Movie>>() {
//            @Override
//            protected List<Movie> doInBackground(Void... voids) {
//                // Result ArrayList of parsed movies.
//                ArrayList<Movie> movies = new ArrayList<>(0);
//
//                // Get the XML
//                URL url;
//                try {
////                String feed =
////                        getApplication().getString(R.string.earthquake_feed);
//                    String endpoint = "";
//                    url = new URL(endpoint);
//                    URLConnection connection;
//                    connection = url.openConnection();
//                    HttpURLConnection httpConnection = (HttpURLConnection) connection;
//                    int responseCode = httpConnection.getResponseCode();
//                    if (responseCode == HttpURLConnection.HTTP_OK) {
//                        InputStream in = httpConnection.getInputStream();
//                        DocumentBuilderFactory dbf =
//                                DocumentBuilderFactory.newInstance();
//                        DocumentBuilder db = dbf.newDocumentBuilder();
//
//                        // Parse the earthquake feed.
//                        Document dom = db.parse(in);
//                        Element docEle = dom.getDocumentElement();
//
//                        // Get a list of each earthquake entry.
//                        NodeList nl = docEle.getElementsByTagName("entry");
//                        if (nl != null && nl.getLength() > 0) {
//                            for (int i = 0; i < nl.getLength(); i++) {
//                                // Check to see if our loading has been cancelled, in which
//                                // case return what we have so far.
//                                if (isCancelled()) {
//                                    Log.d(TAG, "Loading Cancelled");
//                                    return movies;
//                                }
//
//                                int id = 0;
//                                String imdbId = "";
//                                String title = "";
//                                String overview = "";
//
//                                final Movie movie = new Movie(id, imdbId, title, overview);
//
//                                // Add the new earthquake to our result array.
//                                movies.add(movie);
//                            }
//                        }
//                    }
//                    httpConnection.disconnect();
//                } catch (MalformedURLException e) {
//                    Log.e(TAG, "MalformedURLException", e);
//                } catch (IOException e) {
//                    Log.e(TAG, "IOException", e);
//                } catch (ParserConfigurationException e) {
//                    Log.e(TAG, "Parser Configuration Exception", e);
//                } catch (SAXException e) {
//                    Log.e(TAG, "SAX Exception", e);
//                }
//
//                // Return our result array.
//                return movies;
//            }
//
//            @Override
//            protected void onPostExecute(List<Movie> data) {
//                // Update the Live Data with the new list.
//                movies.setValue(data);
//            }
//        }.execute();
    }
}

