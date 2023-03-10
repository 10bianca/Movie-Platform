package input;

import java.util.ArrayList;

public class MoviesInput {

    private String name;
    private String year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;

    private int numLikes;

    private double rating;
    private int numRatings;

    /**
     * @param name
     * @param year
     * @param duration
     * @param genre
     * @param actors
     * @param countriesBanned
     * @param numLikes
     * @param rating
     * @param numRatings
     */
    public MoviesInput(final String name, final String year, final int duration,
                       final ArrayList<String> genre, final ArrayList<String> actors,
                       final ArrayList<String> countriesBanned, final int numLikes,
                       final double rating, final int numRatings) {
        this.name = name;
        this.year = year;
        this.duration = duration;
        this.genres = genre;
        this.actors = actors;
        this.countriesBanned = countriesBanned;
        this.numLikes = numLikes;
        this.rating = rating;
        this.numRatings = numRatings;
    }

    public MoviesInput() {

    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year
     */
    public void setYear(final String year) {
        this.year = year;
    }

    /**
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }

    /**
     * @return
     */
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     * @param actors
     */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    /**
     * @return
     */
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    /**
     * @param countriesBanned
     */
    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    /**
     * @return
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * @param genres
     */
    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    /**
     * @return
     */
    public int getNumLikes() {
        return numLikes;
    }

    /**
     * @param numLikes
     */
    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    /**
     * @return
     */
    public double getRating() {
        return rating;
    }

    /**
     * @param rating
     */
    public void setRating(final double rating) {
        this.rating = rating;
    }

    /**
     * @return
     */
    public int getNumRatings() {
        return numRatings;
    }

    /**
     * @param numRatings
     */
    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

}
