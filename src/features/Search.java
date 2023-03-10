package features;

import input.MoviesInput;

import java.util.ArrayList;

public class Search {
    private ArrayList<MoviesInput> movies;

    /**
     *
     */
    public Search() {

    }

    /**
     * @param movies
     */
    public Search(final ArrayList<MoviesInput> movies) {
        this.movies = movies;
    }

    /**
     * @param movie
     * @return 1 - daca se gaseste un film cu acest nume
     * 0 - altfel
     */
    public ArrayList<MoviesInput> isMovie(final String movie) {
        int i = 0;
        while (i < movies.size()) {
            if (!movies.get(i).getName().startsWith(movie)) {
                movies.remove(i);
                i--;
            }
            i++;
        }
        return movies;
    }


}

