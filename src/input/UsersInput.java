package input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import features.Notifications;
import users.Credentials;

import java.util.ArrayList;


public class UsersInput {

    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies;
    private ArrayList<MoviesInput> purchasedMovies;

    private ArrayList<MoviesInput> watchedMovies;
    private ArrayList<MoviesInput> likedMovies;
    private ArrayList<MoviesInput> ratedMovies;

    private ArrayList<Notifications> notifications;

    @JsonIgnore
    private ArrayList<String> subscribedGenre;

    @JsonIgnore
    private ArrayList<Double> rateForRatedMovies;

    @JsonIgnore
    private static final int TOKENS = 0;

    @JsonIgnore
    private static final int NUM_PREMIUM_MOVIES = 15;


    /**
     * @param credentials
     */
    public UsersInput(final Credentials credentials) {

        this.credentials = credentials;
        this.tokensCount = TOKENS;
        this.numFreePremiumMovies = NUM_PREMIUM_MOVIES;
        this.purchasedMovies = new ArrayList<>();
        this.watchedMovies = new ArrayList<>();
        this.likedMovies = new ArrayList<>();
        this.ratedMovies = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.subscribedGenre = new ArrayList<>();
        this.rateForRatedMovies = new ArrayList<>();

    }

    /**
     *
     */
    public UsersInput() {

        this.tokensCount = TOKENS;
        this.numFreePremiumMovies = NUM_PREMIUM_MOVIES;
        this.purchasedMovies = new ArrayList<>();
        this.watchedMovies = new ArrayList<>();
        this.likedMovies = new ArrayList<>();
        this.ratedMovies = new ArrayList<>();
    }


    /**
     * @return
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * @param credentials
     */
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * @return
     */
    public int getTokensCount() {
        return tokensCount;
    }

    /**
     * @param tokensCount
     */
    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    /**
     * @return
     */
    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    /**
     * @param numFreePremiumMovies
     */
    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    /**
     * @return
     */
    public ArrayList<MoviesInput> getPurchasedMovies() {
        return purchasedMovies;
    }

    /**
     * @param purchasedMovies
     */
    public void setPurchasedMovies(final ArrayList<MoviesInput> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    /**
     * @return
     */
    public ArrayList<MoviesInput> getWatchedMovies() {
        return watchedMovies;
    }

    /**
     * @param watchedMovies
     */
    public void setWatchedMovies(final ArrayList<MoviesInput> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    /**
     * @return
     */
    public ArrayList<MoviesInput> getLikedMovies() {
        return likedMovies;
    }

    /**
     * @param likedMovies
     */
    public void setLikedMovies(final ArrayList<MoviesInput> likedMovies) {
        this.likedMovies = likedMovies;
    }

    /**
     * @return
     */
    public ArrayList<MoviesInput> getRatedMovies() {
        return ratedMovies;
    }

    /**
     * @param ratedMovies
     */
    public void setRatedMovies(final ArrayList<MoviesInput> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    /**
     * @return
     */
    public ArrayList<Notifications> getNotifications() {
        return notifications;
    }

    /**
     * @param notifications
     */
    public void setNotifications(final ArrayList<Notifications> notifications) {
        this.notifications = notifications;
    }

    /**
     * @return
     */
    public ArrayList<String> getSubscribedGenre() {
        return subscribedGenre;
    }

    /**
     * @param subscribedGenre
     */
    public void setSubscribedGenre(final ArrayList<String> subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }

    /**
     * @return
     */
    public ArrayList<Double> getRateForRatedMovies() {
        return rateForRatedMovies;
    }

    /**
     * @param rateForRatedMovies
     */
    public void setRateForRatedMovies(final ArrayList<Double> rateForRatedMovies) {
        this.rateForRatedMovies = rateForRatedMovies;
    }


}
