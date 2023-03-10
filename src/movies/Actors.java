package movies;


public class Actors {

    private static Actors instance = null;
    private static String actors;

    /**
     * @param actors
     */
    public Actors(final String actors) {
        this.actors = actors;
    }

    /**
     *
     */
    private Actors() {

    }

    /**
     * @return
     */
    public Actors getInstance() {
        if (instance == null) {
            instance = new Actors();
        }
        return instance;
    }
}
