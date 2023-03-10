package actions;

import features.Notifications;
import input.MoviesInput;
import input.UsersInput;
import pages.Page;
import com.fasterxml.jackson.databind.node.ArrayNode;
import pages.PageFactory;
import users.Credentials;

import java.util.ArrayList;
import java.util.Objects;

/**
 * se poate schimba doar pagina curenta
 * si lista de filme curenta
 */
public class ChangePage implements Command {
    private Page currentPage;
    private String page;

    private ArrayList<Page> pages;
    private String movie;

    private ArrayList<MoviesInput> currentMovieList;

    private UsersInput currentUser;

    public static class Builder {
        private Page currentPage; //mandatory
        private String page; //mandatory

        private ArrayList<Page> pages; // mandatory

        private String movie; // optional
        private ArrayList<MoviesInput> currentMovieList; //optional
        private UsersInput currentUser; //optional


        /**
         * @param currentPage
         * @param page
         */
        public Builder(final Page currentPage, final String page,
                       final ArrayList<Page> pages1) {
            this.currentPage = currentPage;
            this.page = page;
            this.pages = pages1;
        }


        /**
         * @param movie1
         * @return
         */
        public Builder movie(final String movie1) {
            this.movie = movie1;
            return this;
        }

        /**
         * @param currentMovieList1
         * @return
         */
        public Builder currentMovieList(final ArrayList<MoviesInput> currentMovieList1) {
            this.currentMovieList = currentMovieList1;
            return this;
        }

        /**
         * @param currentUser1
         * @return
         */
        public Builder currentUser(final UsersInput currentUser1) {
            this.currentUser = currentUser1;
            return this;
        }

        /**
         * @return
         */
        public ChangePage build() {
            return new ChangePage(this);
        }
    }

    /**
     * @param builder
     */
    private ChangePage(final Builder builder) {

        this.currentPage = builder.currentPage;
        this.page = builder.page;
        this.pages = builder.pages;
        this.movie = builder.movie;
        this.currentMovieList = builder.currentMovieList;
        this.currentUser = builder.currentUser;

    }

    /**
     *
     */
    public ChangePage() {

    }

    /**
     * @param current
     * @param next
     * @return 0 - daca de pe pagina curenta
     * nu se poate ajunge pe cea ceruta
     * 1 - altfel
     */
    public int match(final Page current, final Page next) {
        if (current.getPage() == PageFactory.PageType.HomePageN) {
            if (!next.getPage().equals(PageFactory.PageType.Login)
                    && !next.getPage().equals(PageFactory.PageType.Register)) {
                return 0;
            }
        } else if (current.getPage() == PageFactory.PageType.HomePageA) {
            if (next.getPage() != PageFactory.PageType.Logout
                    && next.getPage() != PageFactory.PageType.Movies
                    && next.getPage() != PageFactory.PageType.Upgrades) {
                return 0;
            }
        } else if (current.getPage() == PageFactory.PageType.Logout) {
            if (next.getPage() != PageFactory.PageType.HomePageN
                    && next.getPage() != PageFactory.PageType.Login) {
                return 0;
            }
        } else if (current.getPage() == PageFactory.PageType.Movies) {
            if (next.getPage() != PageFactory.PageType.HomePageA
                    && next.getPage() != PageFactory.PageType.Logout
                    && next.getPage() != PageFactory.PageType.SeeDetails
                    && next.getPage() != PageFactory.PageType.Movies) {
                return 0;
            }
        } else if (current.getPage() == PageFactory.PageType.SeeDetails) {
            if (next.getPage() != PageFactory.PageType.HomePageA
                    && next.getPage() != PageFactory.PageType.Movies
                    && next.getPage() != PageFactory.PageType.Upgrades
                    && next.getPage() != PageFactory.PageType.Logout) {
                return 0;
            }
        } else if (current.getPage() == PageFactory.PageType.Upgrades) {
            if (next.getPage() != PageFactory.PageType.HomePageA
                    && next.getPage() != PageFactory.PageType.Movies
                    && next.getPage() != PageFactory.PageType.Logout) {
                return 0;
            }
        } else if (current.getPage() == PageFactory.PageType.Register) {
            if (next.getPage() != PageFactory.PageType.HomePageA) {
                return 0;
            }
        } else if (current.getPage() == PageFactory.PageType.Login) {
            if (next.getPage() != PageFactory.PageType.HomePageA) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * @return
     */
    public UsersInput deepCopyCurrentUser() {
        /**
         * deep copy pentru currentUser
         */
        String name = currentUser.getCredentials().getName();
        String password = currentUser.getCredentials().getPassword();
        String accountType = currentUser.getCredentials().getAccountType();
        String country = currentUser.getCredentials().getCountry();
        String balance = currentUser.getCredentials().getBalance();

        UsersInput user = new UsersInput(new Credentials(name, password,
                accountType, country, balance));
        user.setTokensCount(currentUser.getTokensCount());
        user.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies());

        ArrayList<MoviesInput> purchasedMovies =
                deepCopyCurrentMovieList(currentUser.getPurchasedMovies());
        user.setPurchasedMovies(purchasedMovies);

        ArrayList<MoviesInput> watchedMovies =
                deepCopyCurrentMovieList(currentUser.getWatchedMovies());
        user.setWatchedMovies(watchedMovies);

        ArrayList<MoviesInput> likedMovies =
                deepCopyCurrentMovieList(currentUser.getLikedMovies());
        user.setLikedMovies(likedMovies);

        ArrayList<MoviesInput> ratedMovies =
                deepCopyCurrentMovieList(currentUser.getRatedMovies());
        user.setRatedMovies(ratedMovies);

        ArrayList<Notifications> notifications = deepCopyNotifications();
        user.setNotifications(notifications);
        return user;
    }

    /**
     * @return
     */
    public int returnError(final Page nextPage) {

        if (match(currentPage, nextPage) == 0) {
            return 0;
        }
        if (nextPage.getPage() == PageFactory.PageType.SeeDetails) {
            for (int i = 0; i < currentMovieList.size(); i++) {
                if (Objects.equals(currentMovieList.get(i).getName(), movie)) {
                    return 1;
                }
            }
            return 0;
        }
        return 1;
    }

    /**
     * @return
     */
    public ArrayList<Notifications> deepCopyNotifications() {
        ArrayList<Notifications> notifications = new ArrayList<>();

        if (currentUser.getNotifications() != null) {
            for (int i = 0; i < currentUser.getNotifications().size(); i++) {
                String movieName = currentUser.getNotifications().get(i).getMovieName();
                String message = currentUser.getNotifications().get(i).getMessage();
                notifications.add(new Notifications(movieName, message));
            }
        }


        return notifications;
    }

    /**
     * @param list
     * @return
     */
    public ArrayList<MoviesInput> deepCopyCurrentMovieList(
            final ArrayList<MoviesInput> list) {
        /**
         * deep copy currentMovieList
         */
        ArrayList<MoviesInput> movies = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            copy(list, movies, i);
        }
        return movies;
    }

    /**
     * @param list
     * @param movies
     * @param i
     */
    static void copy(final ArrayList<MoviesInput> list,
                     final ArrayList<MoviesInput> movies,
                     final int i) {
        MoviesInput movie = list.get(i);
        String name = movie.getName();
        String year = movie.getYear();
        int duration = movie.getDuration();
        ArrayList<String> genres = movie.getGenres();
        ArrayList<String> actors = movie.getActors();
        ArrayList<String> countriesBanned = movie.getCountriesBanned();
        int numLikes = movie.getNumLikes();
        double rating = movie.getRating();
        int numRatings = movie.getNumRatings();
        movies.add(new MoviesInput(name, year, duration, genres,
                actors, countriesBanned, numLikes, rating, numRatings));
    }

    /**
     * @param output
     */
    public void outputMovieList(final ArrayNode output) {
        UsersInput user = deepCopyCurrentUser();
        ArrayList<MoviesInput> movies = deepCopyCurrentMovieList(currentMovieList);
        output.addObject().putPOJO("error", null).
                putPOJO("currentMoviesList", movies).
                putPOJO("currentUser", user);
    }

    /**
     * @param output
     */
    public void ouputMovie(final ArrayNode output) {
        UsersInput user = deepCopyCurrentUser();
        int poz = 0;
        for (int i = 0; i < currentMovieList.size(); i++) {
            if (Objects.equals(currentMovieList.get(i).getName(), movie)) {
                poz = i;
            }
        }
        ArrayList<MoviesInput> emptyList = new ArrayList<>();
        emptyList.add(currentMovieList.get(poz));
        emptyList = deepCopyCurrentMovieList(emptyList);
        output.addObject().putPOJO("error", null).
                putPOJO("currentMoviesList", emptyList).
                putPOJO("currentUser", user);

    }

    /**
     * @param output
     */
    public void error(final ArrayNode output) {
        ArrayList<MoviesInput> emptyMovies = new ArrayList<>();
        output.addObject().put("error", "Error").
                putPOJO("currentMoviesList", emptyMovies).
                putPOJO("currentUser", null);
    }

    /**
     *
     * @param output
     */
    @Override
    public void execute(final ArrayNode output) {
        /**
         * verificam tipul paginei pe care vrem sa navigam
         */
        Page nextPage = PageFactory.createPage(page);
        navigate(output, nextPage);
    }

    /**
     *
     * @param output
     */
    @Override
    public void undo(final ArrayNode output) {
        int size = pages.size();
        if (size == 1) {
            error(output);
        } else {
            Page previousPage = pages.get(size - 2);
            if (previousPage.getPage() == PageFactory.PageType.Login
                  || previousPage.getPage() == PageFactory.PageType.Register) {
                pages.remove(size - 2);
                error(output);
            } else {
                navigate(output, previousPage);
                pages.remove(size - 2);
            }
        }
    }

    /**
     * @param output
     * @param previousPage
     */
    private void navigate(final ArrayNode output, final Page previousPage) {
        if (previousPage != null) {
            if (returnError(previousPage) == 1) {
                pages.add(previousPage);
                currentPage.setPage(previousPage.getPage());
                if (currentPage.getPage() == PageFactory.PageType.Movies) {
                    outputMovieList(output);
                } else if (currentPage.getPage() == PageFactory.PageType.SeeDetails) {
                    ouputMovie(output);
                } else if (currentPage.getPage() == PageFactory.PageType.HomePageN) {
                    pages.clear();
                }
            } else {
                error(output);
            }
        }
    }


}
