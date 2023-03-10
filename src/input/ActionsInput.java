package input;

import features.Filters;
import users.Credentials;

import java.util.Objects;

public class ActionsInput {

    private String type;
    private String page;
    private String movie;
    private String feature;
    private Credentials credentials;
    private Filters filters;
    private String startsWith;

    private int count;

    private int rate;

    private String subscribedGenre;

    private String deletedMovie;

    private MoviesInput addedMovie;

    /**
     * @param type
     * @param page
     * @param movie
     * @param feature
     * @param credentials
     * @param filters
     * @param startsWith
     * @param count
     * @param rate
     * @param subscribedGenre
     * @param addedMovie
     * @param deletedMovie
     */
    public ActionsInput(final String type, final String page,
                        final String movie, final String feature,
                        final Credentials credentials, final Filters filters,
                        final String startsWith, final int count, final int rate,
                        final String subscribedGenre, final String deletedMovie,
                        final MoviesInput addedMovie) {

        this.type = type;
        this.page = page;
        this.movie = movie;
        this.feature = feature;
        this.credentials = credentials;
        this.filters = filters;
        this.startsWith = startsWith;
        this.count = count;
        this.rate = rate;
        this.subscribedGenre = subscribedGenre;
        this.deletedMovie = deletedMovie;
        this.addedMovie = addedMovie;
    }

    /**
     *
     */
    public ActionsInput() {

    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @return
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page
     */
    public void setPage(final String page) {
        this.page = page;
    }

    /**
     * @return
     */
    public String getMovie() {
        return movie;
    }

    /**
     * @param movie
     */
    public void setMovie(final String movie) {
        this.movie = movie;
    }

    /**
     * @return
     */
    public String getFeature() {
        return feature;
    }

    /**
     * @param feature
     */
    public void setFeature(final String feature) {
        this.feature = feature;
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
    public Filters getFilters() {
        return filters;
    }

    /**
     * @param filters
     */
    public void setFilters(final Filters filters) {
        this.filters = filters;
    }

    /**
     * @return
     */
    public String getStartsWith() {
        return startsWith;
    }

    /**
     * @param startsWith
     */
    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    /**
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count
     */
    public void setCount(final int count) {
        this.count = count;
    }

    /**
     * @return
     */
    public int getRate() {
        return rate;
    }

    /**
     * @param rate
     */
    public void setRate(final int rate) {
        this.rate = rate;
    }

    /**
     * @return
     */
    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    /**
     * @param subscribedGenre
     */
    public void setSubscribedGenre(final String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }

    /**
     * @return
     */
    public String getDeletedMovie() {
        return deletedMovie;
    }

    /**
     * @param deletedMovie
     */
    public void setDeletedMovie(final String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }

    /**
     * @return
     */
    public MoviesInput getAddedMovie() {
        return addedMovie;
    }

    /**
     * @param addedMovie
     */
    public void setAddedMovie(final MoviesInput addedMovie) {
        this.addedMovie = addedMovie;
    }

    /**
     * @return - 0 for action "on page"
     * - 1 for action "change page"
     * - 2 for "back"
     * - -1 for database
     */
    public int verifyType(final String types) {
        if (Objects.equals(types, "on page")) {
            return 0;
        } else if (Objects.equals(types, "change page")) {
            return 1;
        } else if (Objects.equals(types, "back")) {
            return 2;
        } else {
            return -1;
        }
    }
}
