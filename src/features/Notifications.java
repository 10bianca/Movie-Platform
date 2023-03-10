package features;

public class Notifications {

    private String movieName;
    private String message;

    /**
     *
     */
    public Notifications() {

    }

    /**
     * @param movieName
     * @param message
     */
    public Notifications(final String movieName, final String message) {
        this.movieName = movieName;
        this.message = message;
    }

    /**
     * @return
     */
    public String getMovieName() {
        return movieName;
    }

    /**
     * @param movieName
     */
    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }

    /**
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(final String message) {
        this.message = message;
    }


}
