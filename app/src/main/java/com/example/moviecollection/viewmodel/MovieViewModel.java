package com.example.moviecollection.viewmodel;

import android.app.Application;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviecollection.model.Movie;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MovieViewModel extends AndroidViewModel {
    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    private static MutableLiveData<List<Movie>> movies;
    private static final String TAG = "MoviesUpdate";

    public LiveData<List<Movie>> getMovies() {
        if (movies == null) {
            movies = new MutableLiveData<List<Movie>>();
            loadMovies();
        }
        return movies;
    }

    public static void loadMovies() {
        new AsyncTask<Void, Void, List<Movie>>() {
            @Override
            protected List<Movie> doInBackground(Void... voids) {
                // Result ArrayList of parsed movies.
                ArrayList<Movie> movies = new ArrayList<>(0);

                // Get the XML
                URL url;
                try {
//                String feed =
//                        getApplication().getString(R.string.earthquake_feed);
                    String endpoint = "";
                    url = new URL(endpoint);
                    URLConnection connection;
                    connection = url.openConnection();
                    HttpURLConnection httpConnection = (HttpURLConnection) connection;
                    int responseCode = httpConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream in = httpConnection.getInputStream();
                        DocumentBuilderFactory dbf =
                                DocumentBuilderFactory.newInstance();
                        DocumentBuilder db = dbf.newDocumentBuilder();

                        // Parse the earthquake feed.
                        Document dom = db.parse(in);
                        Element docEle = dom.getDocumentElement();

                        // Get a list of each earthquake entry.
                        NodeList nl = docEle.getElementsByTagName("entry");
                        if (nl != null && nl.getLength() > 0) {
                            for (int i = 0; i < nl.getLength(); i++) {
                                // Check to see if our loading has been cancelled, in which
                                // case return what we have so far.
                                if (isCancelled()) {
                                    Log.d(TAG, "Loading Cancelled");
                                    return movies;
                                }
//                            Element entry =
//                                    (Element)nl.item(i);
//                            Element id =
//                                    (Element)entry.getElementsByTagName("id").item(0);
//                            Element title =
//                                    (Element)entry.getElementsByTagName("title").item(0);
//                            Element g =
//                                    (Element)entry.getElementsByTagName("georss:point")
//                                            .item(0);
//                            Element when =
//                                    (Element)entry.getElementsByTagName("updated").item(0);
//                            Element link =
//                                    (Element)entry.getElementsByTagName("link").item(0);
//                            String idString = id.getFirstChild().getNodeValue();
//                            String details = title.getFirstChild().getNodeValue();
//                            String hostname = "http://earthquake.usgs.gov";
//                            String linkString = hostname + link.getAttribute("href");
//                            String point = g.getFirstChild().getNodeValue();
//                            String dt = when.getFirstChild().getNodeValue();
//                            SimpleDateFormat sdf =
//                                    new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
//                            Date qdate = new GregorianCalendar(0,0,0).getTime();
//                            try {
//                                qdate = sdf.parse(dt);
//                            } catch (ParseException e) {
//                                Log.e(TAG, "Date parsing exception.", e);
//                            }
//                            String[] location = point.split(" ");
//                            Location l = new Location("dummyGPS");
//                            l.setLatitude(Double.parseDouble(location[0]));
//                            l.setLongitude(Double.parseDouble(location[1]));
//                            String magnitudeString = details.split(" ")[1];
//                            int end = magnitudeString.length()-1;
//                            double magnitude =
//                                    Double.parseDouble(magnitudeString.substring(0, end));
//                            if (details.contains("-"))
//                                details = details.split("-")[1].trim();
//                            else
//                                details = "";


                                int id = 0;
                                String imdbId = "";
                                String title = "";
                                String overview = "";

                                final Movie movie = new Movie(id, imdbId, title, overview);

                                // Add the new earthquake to our result array.
                                movies.add(movie);
                            }
                        }
                    }
                    httpConnection.disconnect();
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException", e);
                } catch (IOException e) {
                    Log.e(TAG, "IOException", e);
                } catch (ParserConfigurationException e) {
                    Log.e(TAG, "Parser Configuration Exception", e);
                } catch (SAXException e) {
                    Log.e(TAG, "SAX Exception", e);
                }

                // Return our result array.
                return movies;
            }

            @Override
            protected void onPostExecute(List<Movie> data) {
                // Update the Live Data with the new list.
                movies.setValue(data);
            }
        }.execute();
    }
}

