package features;

import input.MoviesInput;

import java.util.ArrayList;
import java.util.Objects;

public class Contains extends Filters {

    private ArrayList<String> actors;
    private ArrayList<String> genre;

    /**
     *
     */

    public Contains() {

    }

    /**
     * @param actors
     * @param genre
     */
    public Contains(final ArrayList<String> actors,
                    final ArrayList<String> genre) {
        this.actors = actors;
        this.genre = genre;
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
    public ArrayList<String> getGenre() {
        return genre;
    }

    /**
     * @param genre
     */
    public void setGenre(final ArrayList<String> genre) {
        this.genre = genre;
    }

    /**
     * @param currentMovieList
     * @param actorsMovie
     */
    public void containsActorsCurrentMovieList(final ArrayList<MoviesInput> currentMovieList,
                                               final ArrayList<String> actorsMovie) {

        for (int i = 0; i < actorsMovie.size(); i++) {

            int j = 0;
            while (j < currentMovieList.size()) {

                int ok = 0;
                for (int k = 0; k < currentMovieList.get(j).getActors().size(); k++) {
                    if (Objects.equals(actorsMovie.get(i),
                            currentMovieList.get(j).getActors().get(k))) {
                        ok = 1;
                    }
                }
                if (ok == 0) {
                    currentMovieList.remove(j);
                    j--;
                }
                j++;
            }


        }

    }

    /**
     * @param currentMovieList
     * @param genres
     */
    public void containsGenresCurrentMovieList(final ArrayList<MoviesInput> currentMovieList,
                                               final ArrayList<String> genres) {

        for (int i = 0; i < genres.size(); i++) {

            int j = 0;
            while (j < currentMovieList.size()) {

                int ok = 0;
                for (int k = 0; k < currentMovieList.get(j).getGenres().size(); k++) {
                    if (Objects.equals(genres.get(i), currentMovieList.get(j).getGenres().get(k))) {
                        ok = 1;
                    }
                }
                if (ok == 0) {
                    currentMovieList.remove(j);
                    j--;
                }
                j++;
            }


        }

    }


}
