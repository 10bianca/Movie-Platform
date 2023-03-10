package features;

import input.MoviesInput;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.Objects;


public class Sort {

    private String rating;
    private String duration;


    /**
     *
     */
    public Sort() {

    }

    /**
     * @param rating
     * @param duration
     */
    public Sort(final String rating, final String duration) {
        this.rating = rating;
        this.duration = duration;
    }

    /**
     * @return
     */
    public String getRating() {
        return rating;
    }

    /**
     * @param rating
     */
    public void setRating(final String rating) {
        this.rating = rating;
    }

    /**
     * @return
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration
     */
    public void setDuration(final String duration) {
        this.duration = duration;
    }

    /**
     * @param currentMovieList
     * @return
     */
    public ArrayList<MoviesInput> sortCurrentMovieList(
            final ArrayList<MoviesInput> currentMovieList) {
        if (Objects.equals(rating, "decreasing")) {
            currentMovieList.sort(new Comparator<MoviesInput>() {
                @Override
                public int compare(final MoviesInput o1,
                                   final MoviesInput o2) {
                        return Double.compare(o2.getRating(), o1.getRating());
                }
            });
        } else if (Objects.equals(rating, "increasing")) {
            currentMovieList.sort(new Comparator<MoviesInput>() {
                @Override
                public int compare(final MoviesInput o1,
                                   final MoviesInput o2) {
                        return Double.compare(o1.getRating(), o2.getRating());
                }
            });
        }

        if (Objects.equals(duration, "decreasing")) {
            currentMovieList.sort(new Comparator<MoviesInput>() {
                @Override
                public int compare(final MoviesInput o1, final MoviesInput o2) {
                        return Integer.compare(o2.getDuration(), o1.getDuration());
                }
            });
        } else if (Objects.equals(duration, "increasing")) {
            currentMovieList.sort(new Comparator<MoviesInput>() {
                @Override
                public int compare(final MoviesInput o1,
                                   final MoviesInput o2) {
                        return Integer.compare(o1.getDuration(), o2.getDuration());
                }
            });
        }
        return currentMovieList;
    }


}
