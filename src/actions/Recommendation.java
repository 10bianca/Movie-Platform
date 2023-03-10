package actions;

import input.MoviesInput;
import input.UsersInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Recommendation {

    private ArrayList<MoviesInput> likedMovies;
    private ArrayList<MoviesInput> currentMovieList;

    /**
     *
     */
    public Recommendation() {

    }

    /**
     * @param likedMovies
     * @param currentMovieList
     */
    public Recommendation(final ArrayList<MoviesInput> likedMovies,
                          final ArrayList<MoviesInput> currentMovieList) {
        this.likedMovies = likedMovies;
        this.currentMovieList = currentMovieList;
    }

    /**
     * @param user
     * @param movie
     * @return
     */
    public int unwatched(final UsersInput user, final MoviesInput movie) {
        for (int i = 0; i < user.getWatchedMovies().size(); i++) {
            if (Objects.equals(user.getWatchedMovies().get(i).getName(), movie.getName())) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * @param currentUser
     * @return
     */
    public MoviesInput recommendation(final UsersInput currentUser) {

        HashMap<String, Integer> likedGenres = new HashMap<>();

        /**
         * adaugam intr-o lista toate genurile care
         * apar in lista de likedMovies
         */

        for (int i = 0; i < likedMovies.size(); i++) {
            for (int j = 0; j < likedMovies.get(i).getGenres().size(); j++) {
                int ok = 1;
                for (String k : likedGenres.keySet()) {
                    if (Objects.equals(k, likedMovies.get(i).getGenres().get(j))) {
                        int value = likedGenres.get(k);
                        likedGenres.put(k, value + 1);
                        ok = 0;
                    }
                }
                if (ok == 1) {
                    likedGenres.put(likedMovies.get(i).getGenres().get(j), 1);
                }
            }
        }


        int max = 0;
        int poz = 0;
        for (int i = 0; i < currentMovieList.size(); i++) {
            int likes = 0;
            for (int j = 0; j < currentMovieList.get(i).getGenres().size(); j++) {
                for (String k : likedGenres.keySet()) {
                    if (Objects.equals(k, currentMovieList.get(i).getGenres().get(j))) {
                        likes = likes + likedGenres.get(k);
                    }
                }
            }
            if (unwatched(currentUser, currentMovieList.get(i)) == 0) {
                if (likes > max) {
                    poz = i;
                    max = likes;
                } else if (likes == max) {
                    if (currentMovieList.get(poz).getName().
                            compareTo(currentMovieList.get(i).getName()) < 0) {
                        poz = i;
                        max = likes;
                    } else if (currentMovieList.get(poz).getName().
                            compareTo(currentMovieList.get(i).getName()) == 0) {
                        poz = i;
                        max = likes;
                    }
                }
            }
        }
        return currentMovieList.get(poz);
    }
}
