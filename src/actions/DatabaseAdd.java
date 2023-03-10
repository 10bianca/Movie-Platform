package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import features.Notifications;
import input.MoviesInput;
import input.UsersInput;

import java.util.ArrayList;
import java.util.Objects;

public class DatabaseAdd {

    private ArrayList<MoviesInput> movies;

    private ArrayList<UsersInput> users;

    /**
     *
     */
    public DatabaseAdd() {

    }

    /**
     * @param movies
     * @param users
     */
    public DatabaseAdd(final ArrayList<MoviesInput> movies,
                       final ArrayList<UsersInput> users) {
        this.movies = movies;
        this.users = users;
    }

    /**
     * @param moviesInput
     * @return
     */
    public int error(final MoviesInput moviesInput) {

        for (int i = 0; i < movies.size(); i++) {
            if (Objects.equals(movies.get(i).getName(), moviesInput.getName())) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * @param output
     */
    public void outputError(final ArrayNode output) {
        ArrayList<MoviesInput> emptyMovies = new ArrayList<>();
        output.addObject().put("error", "Error").
                putPOJO("currentMoviesList", emptyMovies).
                putPOJO("currentUser", null);
    }

    /**
     * @param moviesInput
     * @return
     */
    public ArrayList<MoviesInput> addMovie(final MoviesInput moviesInput) {

        if (error(moviesInput) == 1) {
            movies.add(moviesInput);
            for (int i = 0; i < users.size(); i++) {
                String countryUser = users.get(i).getCredentials().getCountry();
                ArrayList<String> countryBanned = moviesInput.getCountriesBanned();

                int error = 1;
                for (int j = 0; j < countryBanned.size(); j++) {
                    if (Objects.equals(countryUser, countryBanned.get(j))) {
                        error = 0;
                    }
                }

                /**
                 * daca tara utilizatorul curent nu se afla
                 * printre tarile interzise filmului curent
                 * verificam daca este abonat la
                 * genurile filmului
                 */
                if (error == 1) {
                    ArrayList<String> subscribed = users.get(i).getSubscribedGenre();
                    int notify = 0;
                    if (subscribed != null) {
                        for (int j = 0; j < subscribed.size(); j++) {

                            for (int k = 0; k < moviesInput.getGenres().size(); k++) {
                                if (Objects.equals(subscribed.get(j),
                                        moviesInput.getGenres().get(k))) {
                                    notify = 1;
                                }
                            }
                        }
                    }
                    if (notify == 1) {
                        ArrayList<Notifications> notifications = new ArrayList<>();
                        notifications.add(new Notifications(moviesInput.getName(), "ADD"));
                        users.get(i).setNotifications(notifications);
                    }
                }

            }
        }
        return movies;
    }

}
