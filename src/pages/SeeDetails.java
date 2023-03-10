package pages;

import input.MoviesInput;
import input.UsersInput;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Singleton pattern
 */
public class SeeDetails extends Page {

    private static SeeDetails instance = null;
    private static int contor = 0;

    private UsersInput currentUser;

    private ArrayList<MoviesInput> currentMovieList;

    /**
     * @return
     */
    public static SeeDetails getSeeDetails() {
        if (instance == null) {
            contor++;
            instance = new SeeDetails();
        }
        return instance;
    }

    public static int getNumberOfInstances() {
        return contor;
    }

    /**
     *
     */
    public SeeDetails() {
        super.setPage(PageFactory.PageType.SeeDetails);
    }

    /**
     * @param currentUser
     * @param currentMovieList
     */
    public SeeDetails(final UsersInput currentUser,
                      final ArrayList<MoviesInput> currentMovieList) {
        this.currentUser = currentUser;
        this.currentMovieList = currentMovieList;
    }

    /**
     * @return
     */
    public static SeeDetails getInstance() {
        return instance;
    }

    /**
     * @param instance
     */
    public static void setInstance(final SeeDetails instance) {
        SeeDetails.instance = instance;
    }

    /**
     * @return
     */
    public static int getContor() {
        return contor;
    }

    /**
     * @param contor
     */
    public static void setContor(final int contor) {
        SeeDetails.contor = contor;
    }

    /**
     * @return
     */
    public UsersInput getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser
     */
    public void setCurrentUser(final UsersInput currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @return
     */
    public ArrayList<MoviesInput> getCurrentMovieList() {
        return currentMovieList;
    }

    /**
     * @param currentMovieList
     */
    public void setCurrentMovieList(final ArrayList<MoviesInput> currentMovieList) {
        this.currentMovieList = currentMovieList;
    }

    /**
     * @param movie
     * @return
     */
    public int position(final ArrayList<MoviesInput> list, final String movie) {
        int poz = -1;
        for (int i = 0; i < list.size(); i++) {
            if (Objects.equals(list.get(i).getName(), movie)) {
                poz = i;
            }
        }
        return poz;
    }

    /**
     * @param feature
     * @param movie
     * @return
     */
    public int error(final String feature, final String movie) {

        int error = 0;
        if (Objects.equals(feature, "watch")) {
            ArrayList<MoviesInput> purchasedMovies = currentUser.getPurchasedMovies();
            for (int i = 0; i < purchasedMovies.size(); i++) {
                if (Objects.equals(purchasedMovies.get(i).getName(), movie)) {
                    error = 1;
                }
            }
        } else if (Objects.equals(feature, "like") || Objects.equals(feature, "rate")) {
            ArrayList<MoviesInput> watchedMovies = currentUser.getWatchedMovies();
            for (int i = 0; i < watchedMovies.size(); i++) {
                if (Objects.equals(watchedMovies.get(i).getName(), movie)) {
                    error = 1;
                }
            }
        } else if (Objects.equals(feature, "purchase")) {
            ArrayList<MoviesInput> purchasedMovies = currentUser.getPurchasedMovies();
            if (position(purchasedMovies, movie) == -1) {
                error = 1;
            }
        }
        return error;
    }

    /**
     * @param movie
     * @return
     */
    public UsersInput purchaseMovie(final String movie) {

        ArrayList<MoviesInput> purchasedMovies = currentUser.getPurchasedMovies();
        int numFreePremiumMovies = currentUser.getNumFreePremiumMovies();
        int position = position(currentMovieList, movie);
        MoviesInput copy = currentMovieList.get(position);


        purchasedMovies.add(copy);
        currentUser.setPurchasedMovies(purchasedMovies);
        if (Objects.equals(currentUser.getCredentials().getAccountType(), "premium")) {
            if (numFreePremiumMovies == 0) {
                int tokens = currentUser.getTokensCount();
                currentUser.setTokensCount(tokens - 2);
            } else {
                currentUser.setNumFreePremiumMovies(numFreePremiumMovies - 1);
            }
        } else {
            int tokens = currentUser.getTokensCount();
            currentUser.setTokensCount(tokens - 2);
        }

        return currentUser;
    }

    /**
     * @param movie
     * @return
     */
    public UsersInput watchMovie(final String movie) {
        ArrayList<MoviesInput> watchedMovies = currentUser.getWatchedMovies();
        int position = position(currentMovieList, movie);
        if (position(watchedMovies, movie) == -1) {
            MoviesInput copy = currentMovieList.get(position);
            watchedMovies.add(copy);
            currentUser.setWatchedMovies(watchedMovies);
        }
        return currentUser;
    }

    /**
     * @param movie
     * @param movies
     * @return
     */
    public UsersInput likeMovie(final String movie, final ArrayList<MoviesInput> movies) {
        ArrayList<MoviesInput> likedMovies = currentUser.getLikedMovies();
        int position = position(currentMovieList, movie);
        int position1 = position(movies, movie);
        if (position(likedMovies, movie) == -1) {
            int likesMovie = currentMovieList.get(position).getNumLikes();
            MoviesInput copy = currentMovieList.get(position);
            likedMovies.add(copy);
            currentUser.setLikedMovies(likedMovies);
            movies.get(position1).setNumLikes(likesMovie + 1);
            currentMovieList.get(position).setNumLikes(likesMovie + 1);
        }
        return currentUser;
    }

    /**
     * @param list
     * @param movie
     * @param rating
     * @param numRatings
     */
    public void update(final ArrayList<MoviesInput> list,
                       final String movie,
                       final Double rating,
                       final int numRatings) {
        int position = position(list, movie);
        /**
         * daca lista exista si filmul cerut se afla printre ele
         */
        if (list.size() > 0 && position > -1) {
            list.get(position).setRating(rating);
            list.get(position).setNumRatings(numRatings);
        }
    }

    /**
     * @param movie
     * @param rate
     * @param movies
     * @param sumRate
     * @return
     */
    public UsersInput rateMovie(final String movie,
                                final double rate,
                                final ArrayList<MoviesInput> movies,
                                final ArrayList<Double> sumRate,
                                final ArrayList<UsersInput> users) {

        ArrayList<MoviesInput> ratedMovies = currentUser.getRatedMovies();
        int position = position(currentMovieList, movie);
        int position1 = position(movies, movie);

        int ratedTwice = 0;
        Double newRate = Double.valueOf(0);
        /**
         * verificam daca user-ul curent a mai dat rating acestui film
         */
        for (int i = 0; i < ratedMovies.size(); i++) {
            if (Objects.equals(ratedMovies.get(i).getName(), movie)) {
                ratedTwice = 1;

            }
        }
        int numRatings = movies.get(position1).getNumRatings();
        /**
         * daca filmul nu a mai fost rated de utilizatorul curent
         * se adauga in lista de ratedMovies
         * si numarul de rating uri creste
         */
        if (ratedTwice == 0) {

            movies.get(position1).setNumRatings(numRatings + 1);
            currentMovieList.get(position).setNumRatings(numRatings + 1);

            MoviesInput copy = currentMovieList.get(position);
            ratedMovies.add(copy);
            currentUser.setRatedMovies(ratedMovies);

            /**
             * daca filmul nu a mai fost rated niciodata
             * ratingul ramane cel transmis ca parametru
             * altfel, se aduna rating-ul curent
             * la suma rating-urilor date pana acum si se calculeaza media lor
             */
            if (numRatings == 0) {
                sumRate.set(position, rate);
                newRate = rate;
            } else {
                Double sum = sumRate.get(position);
                sum += rate;
                newRate = sum / (numRatings + 1);
                sumRate.set(position, sum);
            }
            numRatings++;
        } else {
/**
 * daca filmul a mai fost rated de user-ul curent
 * nu se adauga in lista si nu creste nr de rating-uri
 */
            Double sum = sumRate.get(position);
            sum += rate;
            sum -= currentUser.getRateForRatedMovies().get(position);
            newRate = sum / (numRatings);
            sumRate.set(position, sum);

        }

        ArrayList<Double> rateForRatedMovies = currentUser.getRateForRatedMovies();
        rateForRatedMovies.set(position, newRate);
        currentUser.setRateForRatedMovies(rateForRatedMovies);

        movies.get(position1).setRating(newRate);
        currentMovieList.get(position).setRating(newRate);

        /**
         * parcurgem toti userii si actualizam rating-ul
         * si nr de rating-uri
         * la fiecare categorie (purchasedMovies,
         * watchedMovies, likedMovies, ratedMovies)
         */

        for (int i = 0; i < users.size(); i++) {
            ArrayList<MoviesInput> usersPurchasedMovies = users.get(i).getPurchasedMovies();
            ArrayList<MoviesInput> usersWatchedmovies = users.get(i).getWatchedMovies();
            ArrayList<MoviesInput> usersLikedMovies = users.get(i).getLikedMovies();
            ArrayList<MoviesInput> usersRatedMovies = users.get(i).getRatedMovies();
            update(usersPurchasedMovies, movie, newRate, numRatings);
            update(usersWatchedmovies, movie, newRate, numRatings);
            update(usersLikedMovies, movie, newRate, numRatings);
            update(usersRatedMovies, movie, newRate, numRatings);
            users.get(i).setPurchasedMovies(usersPurchasedMovies);
            users.get(i).setWatchedMovies(usersWatchedmovies);
            users.get(i).setLikedMovies(usersLikedMovies);
            users.get(i).setRatedMovies(usersRatedMovies);
        }


        return currentUser;

    }

    /**
     * @param subscribedGenre
     * @param movie
     * @return
     */
    public int errorSubscribe(final String subscribedGenre, final String movie) {
        int error = 0;
        /**
         * daca user-ul este abonat la vreun gen de film
         * daca user-ul e deja abonat la acest gen
         */
        if (currentUser.getSubscribedGenre() != null) {
            ArrayList<String> subscribed = currentUser.getSubscribedGenre();
            for (int i = 0; i < subscribed.size(); i++) {
                if (Objects.equals(subscribed.get(i), subscribedGenre)) {
                    return 0;
                }
            }
        }
        /**
         * daca genul nu se gaseste printre cele reprezentative filmului
         */

        int poz = position(currentMovieList, movie);
        ArrayList<String> genres = currentMovieList.get(poz).getGenres();
        for (int i = 0; i < genres.size(); i++) {
            if (Objects.equals(subscribedGenre, genres.get(i))) {
                error = 1;
            }
        }

        return error;
    }

    /**
     * subscribed genre
     */
    public UsersInput subscribedGenre(final String subscribedGenre) {
        ArrayList<String> subscribed = new ArrayList<>();
        subscribed.add(subscribedGenre);
        currentUser.setSubscribedGenre(subscribed);
        return currentUser;
    }

    /**
     * @param users
     * @param subscribedGenre
     * @return
     */
    public ArrayList<UsersInput> subscribeToUser(final ArrayList<UsersInput> users,
                                                 final String subscribedGenre) {
        ArrayList<String> subscribed = currentUser.getSubscribedGenre();
        for (int i = 0; i < users.size(); i++) {
            if (Objects.equals(users.get(i).getCredentials().getName(),
                    currentUser.getCredentials().getName())
                    && Objects.equals(users.get(i).getCredentials().getPassword(),
                    currentUser.getCredentials().getPassword())) {
                users.get(i).setSubscribedGenre(subscribed);
            }
        }

        return users;
    }

}
